package com.octo.vanillapull.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "QUOTE")
@IdClass(value = QuotePrimaryKey.class)
public class Quote {

	@Id
	@Column(name = "symbol")
	private String symbol;

	@Id
	@Column(name = "ticker")
	private Long ticker;

	@Column(name = "open")
	private Double quoteOpen;

	@Column(name = "low")
	private Double quoteLow;

	@Column(name = "high")
	private Double quoteHigh;

	@Column(name = "spot")
	private Double quoteSpot;

	@Column(name = "volatility")
	private Double rateVolatility;

	@Column(name = "create_ts")
	private Long createTs;

	@Column(name = "receive_ts")
	private Long receiveTs;

	@Column(name = "persistence_ts", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp persistenceTs;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Long getTicker() {
		return ticker;
	}

	public void setTicker(Long ticker) {
		this.ticker = ticker;
	}

	public Double getQuoteOpen() {
		return quoteOpen;
	}

	public void setQuoteOpen(Double quoteOpen) {
		this.quoteOpen = quoteOpen;
	}

	public Double getQuoteLow() {
		return quoteLow;
	}

	public void setQuoteLow(Double quoteLow) {
		this.quoteLow = quoteLow;
	}

	public Double getQuoteHigh() {
		return quoteHigh;
	}

	public void setQuoteHigh(Double quoteHigh) {
		this.quoteHigh = quoteHigh;
	}

	public Double getQuoteSpot() {
		return quoteSpot;
	}

	public void setQuoteSpot(Double quoteSpot) {
		this.quoteSpot = quoteSpot;
	}

	public Double getRateVolatility() {
		return rateVolatility;
	}

	public void setRateVolatility(Double rateVolatility) {
		this.rateVolatility = rateVolatility;
	}

	public void setCreateTs(Long createTs) {
		this.createTs = createTs;
	}

	public Long getCreateTs() {
		return createTs;
	}

	public void setReceiveTs(Long receiveTs) {
		this.receiveTs = receiveTs;
	}

	public Long getReceiveTs() {
		return receiveTs;
	}

	public void setPersistenceTs(Timestamp persistenceTs) {
		this.persistenceTs = persistenceTs;
	}

	public Timestamp getPersistenceTs() {
		return persistenceTs;
	}

	@Override
	public String toString() {
		return "Quote [symbol=" + symbol + ", ticker=" + ticker
				+ ", quoteOpen=" + quoteOpen + ", quoteLow=" + quoteLow
				+ ", quoteHigh=" + quoteHigh + ", quoteSpot=" + quoteSpot
				+ ", rateVolatility=" + rateVolatility + "]";
	}
}
