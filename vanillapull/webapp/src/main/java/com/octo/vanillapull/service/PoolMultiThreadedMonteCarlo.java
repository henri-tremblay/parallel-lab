package com.octo.vanillapull.service;

import com.octo.vanillapull.util.StdRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Henri Tremblay
 */
@Profile("pool")
@Service
public class PoolMultiThreadedMonteCarlo implements PricingService {

  private class MonteCarloTask extends ForkJoinTask<Double> {

    private final long nbIterations;
    private double maturity,spot, strike, volatility;
    double bestPremiumsComputed = 0;

    public MonteCarloTask(long nbIterations, double maturity, double spot, double strike, double volatility) {
      this.nbIterations = nbIterations;
      this.maturity = maturity;
      this.spot = spot;
      this.strike = strike;
      this.volatility = volatility;
    }

    @Override
    public boolean exec() {
      for (long i = 0; i < nbIterations; i++) {
        double gaussian = StdRandom.gaussian();
        double priceComputed = computeMonteCarloIteration(spot, interestRate, volatility, gaussian, maturity);
        double bestPremium = computePremiumForMonteCarloIteration(priceComputed, strike);
        bestPremiumsComputed += bestPremium;
      }
      return true;
    }

    @Override
    public Double getRawResult() {
      return bestPremiumsComputed;
    }

    @Override
    protected void setRawResult(Double value) {
      this.bestPremiumsComputed = value;
    }
  }

  @Value("${monteCarloIterations}")
  private long numberOfIterations;
  @Value("${interestRate}")
  private double interestRate;

  private ForkJoinPool pool;

  @PostConstruct
  public void init() throws Exception {
    pool = new ForkJoinPool();
  }

  @PreDestroy
  public void cleanUp() throws Exception {
    pool.shutdown();
  }

  @Override
  public double calculatePrice(double maturity, double spot, double strike, double volatility) {

    maturity /= 360.0;

    int processors = Runtime.getRuntime().availableProcessors();
    long nbPerThreads = numberOfIterations / processors;

    MonteCarloTask[] tasks = new MonteCarloTask[processors];
    for (int i = 0; i < processors; i++) {
      MonteCarloTask task = new MonteCarloTask(nbPerThreads, maturity, spot, strike, volatility);
      pool.execute(task);
      tasks[i]= task;
    }

    double bestPremiumsComputed = 0;

    for (int i = 0; i < processors; i++) {
      try {
        bestPremiumsComputed += tasks[i].get();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }

    // Compute mean
    double meanOfPremiums = bestPremiumsComputed / (nbPerThreads * processors); // not using numberOfIterations because the rounding might might have truncate some iterations

    // Discount the expected payoff at risk free interest rate
    double pricedValue = Math.exp(-interestRate * maturity) * meanOfPremiums;

    return pricedValue;
  }

  public double computeMonteCarloIteration(double spot, double rate, double volatility, double gaussian, double maturity) {
    double result = spot * Math.exp((rate - Math.pow(volatility, 2) * 0.5) * maturity + volatility * gaussian * Math.sqrt(maturity));
    return result;
  }

  public double computePremiumForMonteCarloIteration(double computedBestPrice, double strike) {
    return Math.max(computedBestPrice - strike, 0);
  }
}
