package com.octo.vanillapull.shared;

import java.io.Serializable;

public class QuoteDisplay implements Serializable {

	private static final long serialVersionUID = -3654096942320115225L;

	private long ticker;
	private String symbol;
	private double quoteOpen;
	private double quoteLow;
	private double quoteHigh;
	private double quoteSpot;
	private double rateVolatility;
	private double rateVariation;
	private long createdTs;
	private long loadedTs;
	private long receivedTs;

	public long getTicker() {
		return ticker;
	}

	public void setTicker(long ticker) {
		this.ticker = ticker;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public double getQuoteOpen() {
		return quoteOpen;
	}

	public void setQuoteOpen(double quoteOpen) {
		this.quoteOpen = quoteOpen;
	}

	public double getQuoteLow() {
		return quoteLow;
	}

	public void setQuoteLow(double quoteLow) {
		this.quoteLow = quoteLow;
	}

	public double getQuoteHigh() {
		return quoteHigh;
	}

	public void setQuoteHigh(double quoteHigh) {
		this.quoteHigh = quoteHigh;
	}

	public double getQuoteSpot() {
		return quoteSpot;
	}

	public void setQuoteSpot(double quoteSpot) {
		this.quoteSpot = quoteSpot;
	}

	public double getRateVolatility() {
		return rateVolatility;
	}

	public void setRateVolatility(double rateVolatility) {
		this.rateVolatility = rateVolatility;
	}

	public double getRateVariation() {
		return rateVariation;
	}

	public void setRateVariation(double rateVariation) {
		this.rateVariation = rateVariation;
	}

	public long getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(long createdTs) {
		this.createdTs = createdTs;
	}

	public long getLoadedTs() {
		return loadedTs;
	}

	public void setLoadedTs(long loadedTs) {
		this.loadedTs = loadedTs;
	}

	public long getReceivedTs() {
		return receivedTs;
	}

	public void setReceivedTs(long receivedTs) {
		this.receivedTs = receivedTs;
	}

	@Override
	public String toString() {
		return "QuoteDisplay [symbol=" + symbol + ", quoteOpen=" + quoteOpen + ", quoteLow="
				+ quoteLow + ", quoteHigh=" + quoteHigh + ", quoteSpot=" + quoteSpot + ", rateVolatility="
				+ rateVolatility + ", rateVariation=" + rateVariation + "]";
	}
}
