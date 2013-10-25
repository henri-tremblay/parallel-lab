package com.octo.vanillapull.service;

/**
 * @author Henri Tremblay
 */
public interface PricingService {
	double calculatePrice(double maturity, double spot, double strike,
			double volatility);
}
