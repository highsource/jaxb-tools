package org.jvnet.hyperjaxb3.ejb.test.tests;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class C {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Object[] d;

	@Transient
	public Object[] getD() {
		return d;
	}

	public void setD(Object[] d) {
		this.d = d;
	}

	private J<Object[]> e;

	@Transient
	public J<Object[]> getE() {
		return e;
	}

	public void setE(J<Object[]> e) {
		this.e = e;
	}

	private J<Object> f;

	@Transient
	public J<Object> getF() {
		return f;
	}

	public void setF(J<Object> f) {
		this.f = f;
	}

	public static class J<T> {
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
