package org.jvnet.hyperjaxb3.util.tests;

import java.io.Serializable;

import org.jvnet.hyperjaxb3.item.Item;

public class StringItem implements Item<String>, Serializable {

	private static final long serialVersionUID = 1L;

	private String value;

	public void setItem(String value) {
		this.value = value;
	}

	public String getItem() {
		return this.value;
	}

}
