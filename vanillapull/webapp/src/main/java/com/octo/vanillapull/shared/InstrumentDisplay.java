package com.octo.vanillapull.shared;

import java.io.Serializable;

public class InstrumentDisplay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 469538729919494207L;

	private String symbol;
	private String label;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
