package org.jvnet.jaxb.annox.samples.po;

import java.math.BigDecimal;

public class USAddress {

	protected String name;

	protected String street;

	protected String city;

	protected String state;

	protected BigDecimal zip;

	protected String country;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String value) {
		this.street = value;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String value) {
		this.state = value;
	}

	public BigDecimal getZip() {
		return zip;
	}

	public void setZip(BigDecimal value) {
		this.zip = value;
	}

	public String getCountry() {
		if (country == null) {
			return "US";
		} else {
			return country;
		}
	}

	public void setCountry(String value) {
		this.country = value;
	}

}
