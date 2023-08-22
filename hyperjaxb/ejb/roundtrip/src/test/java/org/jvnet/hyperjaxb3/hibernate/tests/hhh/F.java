package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class F {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}