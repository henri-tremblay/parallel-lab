package com.octo.vanillapull.service;

import akka.actor.*;
import akka.actor.Status.Success;
import akka.routing.RoundRobinRouter;
import akka.util.Timeout;

import com.octo.vanillapull.actor.Master;
import com.octo.vanillapull.actor.ResultListener;
import com.octo.vanillapull.actor.Work;
import com.octo.vanillapull.util.StdRandom;
import com.octo.vanillapull.web.ContextInitializer;
import com.sun.swing.internal.plaf.synth.resources.synth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public final static Logger logger = LoggerFactory.getLogger(AkkaMonteCarlo.class);
	

	public static final int processors = Runtime.getRuntime()
			.availableProcessors();
	@Value("${monteCarloIterations}")
	long numberOfIterations;
	@Value("${interestRate}")
	double interestRate;

	int nb = 0;
	ActorSystem system;
	ActorRef master;

	@PostConstruct
	public void init() {
		// Create an Akka system
		system = ActorSystem.create("MonteCarloSystem");

		// create the master
		master = system.actorOf(new Props(new UntypedActorFactory() {
			public UntypedActor create() {
				return new Master(interestRate);
			}
		}));
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		system.shutdown();
	}

	@Override
	public double calculatePrice(final double maturity, double spot,
			double strike, double volatility) {

		// start the calculation
		Work work = new Work(numberOfIterations, maturity, spot, strike,
				volatility);

		// Creating agregator - use to aggregate result from master
		ActorRef agregator = system.actorOf(new Props(new UntypedActorFactory() {
			@Override
			public UntypedActor create() throws Exception {
				ResultListener agregator = new ResultListener();
				agregator.maturity = maturity;
				agregator.master = master;
				agregator.interestRate = interestRate;
				return agregator;
			}
		}));

		Timeout timeout = new Timeout(Duration.create(60, "seconds"));
		Future<Object> future = ask(agregator, work, timeout);

		double bestPremiumsComputed = 0;
		try {
			if(logger.isDebugEnabled()) logger.debug("[SERVICE] Waiting thread");
			bestPremiumsComputed = (Double) Await.result(future,
					timeout.duration());
			if(logger.isDebugEnabled()) logger.debug("[SERVICE] Releasing thread");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

		}

		return bestPremiumsComputed;
	}

}
