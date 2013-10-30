package com.octo.vanillapull.actor;

public class Work {
	long nbIterations;
	double maturity, spot, strike, volatility;

	public Work(long nbIterations, double maturity, double spot,
			double strike, double volatility) {
		this.nbIterations = nbIterations;
		this.maturity = maturity;
		this.spot = spot;
		this.strike = strike;
		this.volatility = volatility;
	}

	
	
	
}
