package com.octo.vanillapull.service;

import akka.actor.*;
import akka.routing.RoundRobinRouter;
import akka.util.Timeout;
import com.octo.vanillapull.util.StdRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static akka.pattern.Patterns.*;

/**
 * @author Henri Tremblay
 */
@Profile("akka")
@Service
public class AkkaMonteCarlo implements PricingService {

  private static final int processors = Runtime.getRuntime().availableProcessors();
  @Value("${monteCarloIterations}")
  long numberOfIterations;
  @Value("${interestRate}")
  double interestRate;

  public static double computeMonteCarloIteration(double spot, double rate, double volatility, double gaussian, double maturity) {
    double result = spot * Math.exp((rate - Math.pow(volatility, 2) * 0.5) * maturity + volatility * gaussian * Math.sqrt(maturity));
    return result;
  }

  public static double computePremiumForMonteCarloIteration(double computedBestPrice, double strike) {
    return Math.max(computedBestPrice - strike, 0);
  }

  @PostConstruct
  public void init() throws Exception {

  }

  @PreDestroy
  public void cleanUp() throws Exception {

  }

  @Override
  public double calculatePrice(double maturity, double spot, double strike, double volatility) {
    // Create an Akka system
    ActorSystem system = ActorSystem.create("MonteCarloSystem");

    // create the master
    ActorRef master;master = system.actorOf(new Props(new UntypedActorFactory() {
      public UntypedActor create() {
        return new Master(interestRate);
      }
    }), "master");

    // start the calculation
    Work work = new Work(numberOfIterations, maturity, spot, strike, volatility);

    Timeout timeout = new Timeout(Duration.create(60, "seconds"));
    Future<Object> future = ask(master, work, timeout);

    double bestPremiumsComputed = 0;
    try {
      bestPremiumsComputed = (Double) Await.result(future, timeout.duration());
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      system.shutdown();
    }

    return bestPremiumsComputed;
  }

  private static class Work {
    long nbIterations;
    double maturity, spot, strike, volatility;

    public Work(long nbIterations, double maturity, double spot, double strike, double volatility) {
      this.nbIterations = nbIterations;
      this.maturity = maturity;
      this.spot = spot;
      this.strike = strike;
      this.volatility = volatility;
    }
  }

  private static class Master extends UntypedActor {
    private final ActorRef workerRouter;
    private double bestPremiumsComputed = 0;
    private int answerReceived = 0;
    private long nbPerThreads;
    private double maturity;
    private double interestRate;
    private ActorRef replayTo;

    public Master(final double interestRate) {
      workerRouter = this.getContext().actorOf(new Props(new UntypedActorFactory() {
        public UntypedActor create() {
          return new Worker(interestRate);
        }
      }).withRouter(new RoundRobinRouter(processors)), "workerRouter");
      this.interestRate = interestRate;
    }

    public void onReceive(Object message) {
      if (message instanceof Work) {
        Work work = (Work) message;

        replayTo = getSender();
        nbPerThreads = work.nbIterations / processors;
        maturity = work.maturity / 360.0;

        // Modify the message to give the right to the workers
        work.nbIterations = nbPerThreads;
        work.maturity = maturity;
        for (int i = 0; i < processors; i++) {
          workerRouter.tell(work, getSelf());
        }
        return;
      }

      if (message instanceof Double) {
        Double result = (Double) message;
        bestPremiumsComputed += result;
        if (++answerReceived == processors) {
          // Compute mean
          double meanOfPremiums = bestPremiumsComputed / (nbPerThreads * processors); // not using numberOfIterations because the rounding might might have truncate some iterations

          // Discount the expected payoff at risk free interest rate
          double pricedValue = Math.exp(-interestRate * maturity) * meanOfPremiums;

          // Return the answer
          replayTo.tell(pricedValue, getSelf());
        }
        return;
      }
      unhandled(message);
    }
  }

  private static class Worker extends UntypedActor {

    private double interestRate;

    public Worker(double interestRate) {
      this.interestRate = interestRate;
    }

    public void onReceive(Object message) {
      if (!(message instanceof Work)) {
        unhandled(message);
        return;
      }

      Work work = (Work) message;

      double bestPremiumsComputed = 0;

      for (long i = 0; i < work.nbIterations; i++) {
        double gaussian = StdRandom.gaussian();
        double priceComputed = computeMonteCarloIteration(work.spot, interestRate, work.volatility, gaussian, work.maturity);
        double bestPremium = computePremiumForMonteCarloIteration(priceComputed, work.strike);
        bestPremiumsComputed += bestPremium;
      }

      getSender().tell(bestPremiumsComputed, getSelf());
    }
  }
}
