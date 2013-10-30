package com.octo.vanillapull.service;

import com.octo.vanillapull.repository.ParameterDao;
import com.octo.vanillapull.util.Gaussian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("vanilla")
public class BSPricingService implements PricingService {

	@Autowired
	private ParameterDao parameterDao;

	@Override public double calculatePrice(double maturity, double spot, double strike, double volatility) {

		// // Retrieve interest rate from database parameters
		double interestRate = parameterDao.findById("INTEREST_RATE").getDoubleValue();

		maturity /= 360.0;

		// from http://introcs.cs.princeton.edu/22library/
		return callPrice(spot, strike, interestRate, volatility,
				maturity);
	}

	/**
	 * Black-Scholes formula
	 * 
	 * @param S
	 * @param X
	 * @param r
	 * @param sigma
	 * @param T
	 * @return
	 */
	public static double callPrice(double S, double X, double r, double sigma, double T) {
		double d1 = (Math.log(S / X) + (r + sigma * sigma / 2) * T)
				/ (sigma * Math.sqrt(T));
		double d2 = d1 - sigma * Math.sqrt(T);
		return S * taylogPhi(d1) - X * Math.exp(-r * T) * Gaussian.Phi(d2);
	}

	/**
	 * return Phi(z) = standard Gaussian cdf using Taylor approximation
	 * 
	 * @param z
	 * @return
	 */
	public static double taylogPhi(double z) {
		if (z < -8.0)
			return 0.0;
		if (z > 8.0)
			return 1.0;
		double sum = 0.0, term = z;
		for (int i = 3; sum + term != sum; i += 2) {
			sum = sum + term;
			term = term * z * z / i;
		}
		return 0.5 + sum * phi(z);
	}

	/**
	 * return phi(x) = standard Gaussian pdf
	 * 
	 * @param x
	 * @return
	 */
	public static double phi(double x) {
		return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
	}

	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}
}
