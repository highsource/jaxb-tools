package org.jvnet.hyperjaxb3.eg.tests;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="autotask", namespace="urn:test")
public class AutoTask extends Node 
{
	private String action;

	/**
	 * @return the action
	 */
	@XmlElement(name="action", namespace="urn:test")
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
