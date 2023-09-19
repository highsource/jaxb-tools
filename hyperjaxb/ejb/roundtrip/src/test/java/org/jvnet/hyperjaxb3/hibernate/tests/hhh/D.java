package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class D {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private E e;

	@Embedded
	@AssociationOverride(name = "f"/*, joinColumns = { @JoinColumn(name = "E_F") }*/)
	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}
}
