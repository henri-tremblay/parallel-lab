package com.octo.vanillapull.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INSTRUMENT")
public class Instrument {

	@Id
	@Column(name = "symbol")
	private String symbol;

	@Column(name = "label")
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

	@Override
	public String toString() {
		return "Instrument [symbol=" + symbol + ", label=" + label + "]";
	}

}
