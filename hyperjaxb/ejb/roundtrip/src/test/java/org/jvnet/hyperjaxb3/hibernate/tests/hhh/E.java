package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

@Embeddable
public class E {

	private List<F> f;

	@OneToMany
	public List<F> getF() {
		return f;
	}

	public void setF(List<F> f) {
		this.f = f;
	}
}
