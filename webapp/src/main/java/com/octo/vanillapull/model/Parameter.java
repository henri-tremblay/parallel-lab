package com.octo.vanillapull.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "PARAM")
public class Parameter {

	public Parameter() {
	}

	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Id
	@Column(name = "KEY", length = 30, nullable = false)
	private String key;

	@Column(name = "VALUE", length = 255, nullable = false)
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

  @Transient
  public double getDoubleValue() {
    return Double.parseDouble(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Parameter parameter = (Parameter) o;

    if (!key.equals(parameter.key)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return "{" + key + '=' + value + '}';
  }
}
