package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
