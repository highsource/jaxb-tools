package org.jvnet.hyperjaxb3.ejb.test.tests;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jvnet.jaxb.lang.Equals;
import org.jvnet.jaxb.lang.EqualsStrategy;
import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "K", propOrder = { "id", "one", "two", "kthree" })
@Entity
@Table(name = "table_kl")
@SecondaryTable(name = "table_k", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "id") })
public class K implements Equals {

	@XmlAttribute
	private String id;

	@Id
	// @Column(table="table_k")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String one;

	@Basic
	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	private String two;

	@Basic
	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	private String kthree;

	@Basic
	@Column(table = "table_k")
	public String getKthree() {
		return kthree;
	}

	public void setKthree(String kthree) {
		this.kthree = kthree;
	}

	@Override
	public boolean equals(Object obj) {
		return equals(null, null, obj, JAXBEqualsStrategy.INSTANCE);
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {

		if (!(object instanceof K)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final K that = (K) object;
		return strategy.equals(null, null, this.getId(), that.getId(), this.id != null, that.id != null)
				&& strategy.equals(null, null, this.getOne(), that.getOne(), this.one != null, that.one != null)
				&& strategy.equals(null, null, this.getTwo(), that.getTwo(), this.two != null, that.two != null)
				&& strategy.equals(null, null, this.getKthree(), that.getKthree(), this.kthree != null, that.kthree != null);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
