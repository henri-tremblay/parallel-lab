package com.octo.vanillapull.service;

import com.octo.vanillapull.util.StdRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @author Henri Tremblay
 */
@Profile("naive")
@Service
public class NaiveMultiThreadedMonteCarlo implements PricingService {

  private class MonteCarloThread extends Thread {

    private final long nbIterations;
    private double maturity,spot, strike, volatility;
    double bestPremiumsComputed = 0;
    private CountDownLatch latch;

    public MonteCarloThread(long nbIterations, double maturity, double spot, double strike, double volatility, CountDownLatch latch) {
      this.nbIterations = nbIterations;
      this.maturity = maturity;
      this.spot = spot;
      this.strike = strike;
      this.volatility = volatility;
      this.latch = latch;
    }

    @Override
    public void run() {
      for (long i = 0; i < nbIterations; i++) {
        double gaussian = StdRandom.gaussian();
        double priceComputed = computeMonteCarloIteration(spot, interestRate, volatility, gaussian, maturity);
        double bestPremium = computePremiumForMonteCarloIteration(priceComputed, strike);
        bestPremiumsComputed += bestPremium;
      }

      latch.countDown();
    }
  }

  @Value("${monteCarloIterations}")
  long numberOfIterations;
  @Value("${interestRate}")
  double interestRate;

  private final int processors = Runtime.getRuntime().availableProcessors();

  @Override
  public double calculatePrice(double maturity, double spot, double strike, double volatility) {

    maturity /= 360.0;

    long nbPerThreads = numberOfIterations / processors;

    CountDownLatch latch = new CountDownLatch(processors);

    MonteCarloThread[] threads = new MonteCarloThread[processors];
    for (int i = 0; i < processors; i++) {
      MonteCarloThread thread = new MonteCarloThread(nbPerThreads, maturity, spot, strike, volatility, latch);
      thread.setName(Thread.currentThread() + " monteCarlo " + i);
      thread.start();
      threads[i]= thread;
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    double bestPremiumsComputed = 0;

    for (int i = 0; i < processors; i++) {
      bestPremiumsComputed += threads[i].bestPremiumsComputed;
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
