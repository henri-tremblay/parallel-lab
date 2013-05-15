package com.octo.vanillapull.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INSTRUMENT")
public class Instrument {

  @Id
  @Column(name = "SYMBOL", length = 5, nullable = false)
  private String symbol;
  @Column(name = "LABEL", length = 30, nullable = false)
  private String label;
  @Column(name = "SPOT", nullable = false)
  private double spot;
  @Column(name = "VOLATILITY", nullable = false)
  private double volatility;
  @Column(name = "VARIATION", nullable = false)
  private double variation;

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

  public double getSpot() {
    return spot;
  }

  public void setSpot(double spot) {
    this.spot = spot;
  }

  public double getVolatility() {
    return volatility;
  }

  public void setVolatility(double volatility) {
    this.volatility = volatility;
  }

  public double getVariation() {
    return variation;
  }

  public void setVariation(double variation) {
    this.variation = variation;
  }

  @Override
  public String toString() {
    return symbol + "/" + label + "/s" + spot + "/v" + volatility + "/p" + variation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Instrument that = (Instrument) o;

    if (!symbol.equals(that.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }
}
