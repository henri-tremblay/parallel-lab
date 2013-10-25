package com.octo.vanillapull.service;

import com.octo.vanillapull.util.StdRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author Henri Tremblay
 */
@Profile("mono")
@Service
public class MonoThreadedMonteCarlo implements PricingService {

	@Value("${monteCarloIterations}")
	long numberOfIterations;
	@Value("${interestRate}")
	double interestRate;

	@Override
	public double calculatePrice(double maturity, double spot, double strike,
			double volatility) {

		double bestPremiumsComputed = 0;

		maturity /= 360.0;

		for (long i = 0; i < numberOfIterations; i++) {
			double gaussian = StdRandom.gaussian();
			double priceComputed = computeMonteCarloIteration(spot,
					interestRate, volatility, gaussian, maturity);
			double bestPremium = computePremiumForMonteCarloIteration(
					priceComputed, strike);
			bestPremiumsComputed += bestPremium;
		}

		// Compute mean
		double meanOfPremiums = bestPremiumsComputed / numberOfIterations;

		// Discount the expected payoff at risk free interest rate
		double pricedValue = Math.exp(-interestRate * maturity)
				* meanOfPremiums;

		return pricedValue;
	}

	public double computeMonteCarloIteration(double spot, double rate,
			double volatility, double gaussian, double maturity) {
		double result = spot
				* Math.exp((rate - Math.pow(volatility, 2) * 0.5) * maturity
						+ volatility * gaussian * Math.sqrt(maturity));
		return result;
	}

	public double computePremiumForMonteCarloIteration(
			double computedBestPrice, double strike) {
		return Math.max(computedBestPrice - strike, 0);
	}

}
