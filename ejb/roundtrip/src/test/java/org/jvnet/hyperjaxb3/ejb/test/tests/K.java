package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

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
		return strategy.equals(null, null, this.getId(), that.getId())
				&& strategy.equals(null, null, this.getOne(), that.getOne())
				&& strategy.equals(null, null, this.getTwo(), that.getTwo())
				&& strategy.equals(null, null, this.getKthree(),
						that.getKthree());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
