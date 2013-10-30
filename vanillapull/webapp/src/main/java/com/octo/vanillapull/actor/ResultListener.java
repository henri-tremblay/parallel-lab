package com.octo.vanillapull.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.octo.vanillapull.service.AkkaMonteCarlo;

public class ResultListener extends UntypedActor {

	public final static Logger logger = LoggerFactory.getLogger(ResultListener.class);
	
	private long nbPerThreads;
	public double interestRate;
	private double bestPremiumsComputed = 0;
	private int answerReceived = 0;
	public double maturity = 0;

	public ActorRef parent;
	public ActorRef master;

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Result) {

			Result resultContainer = (Result) message;
			Double result = resultContainer.result;
			bestPremiumsComputed += result;

			if (++answerReceived == AkkaMonteCarlo.processors) {
				// Compute mean
				double meanOfPremiums = bestPremiumsComputed
						/ (nbPerThreads * AkkaMonteCarlo.processors); // not
																		// using
				// numberOfIterations because the rounding might might have
				// truncate some iterations

				// Discount the expected payoff at risk free interest rate
				double pricedValue = Math.exp(-interestRate * maturity)
						* meanOfPremiums;

				if(logger.isDebugEnabled()) logger.debug("[LISTENER] send to future :" + message + " to " + getSender());
				// Return the answer
				parent.tell(pricedValue, getSelf());

				// Stopping this actor only use for agregate result
				getContext().system().stop(getSelf());
			}
			return;
		} else if (message instanceof Work) {

			// Store the parent (temp used for Future)
			parent = getSender();
			
			Work work = (Work) message;

			nbPerThreads = work.nbIterations / AkkaMonteCarlo.processors;
			maturity = work.maturity / 360.0;

			// Modify the message to give the right to the workers
			work.nbIterations = nbPerThreads;
			work.maturity = maturity;

			master.tell(work, getSelf());

			return;
		} else {
			unhandled(message);
		}
	}
}
