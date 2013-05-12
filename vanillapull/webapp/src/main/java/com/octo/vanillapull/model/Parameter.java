package com.octo.vanillapull.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@Column(name = "key")
	private String key;

	@Column(name = "value")
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

}
