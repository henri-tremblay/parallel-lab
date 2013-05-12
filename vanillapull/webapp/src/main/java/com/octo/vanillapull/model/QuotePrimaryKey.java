package com.octo.vanillapull.model;

public class QuotePrimaryKey {

	private static final long serialVersionUID = 9139451787830967741L;

	private String symbol;
	private Long ticker;

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

}
