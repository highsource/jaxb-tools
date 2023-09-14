/**
 * 
 */
package org.jvnet.hyperjaxb3.eg.tests;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author bspies
 *
 */
@XmlRootElement(name="usertask", namespace="urn:test")
public class UserTask extends Node 
{
	private String assignee;

	/**
	 * @return the assignee
	 */
	@XmlElement(namespace="urn:test")
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
