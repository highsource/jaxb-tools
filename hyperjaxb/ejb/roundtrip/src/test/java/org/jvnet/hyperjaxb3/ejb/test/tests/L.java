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
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "L", propOrder = { "id", "one", "two", "lthree" })
@Entity
@Table(name = "table_kl")
@SecondaryTable(name = "table_l", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "id") })
public class L implements Equals {

	@XmlAttribute
	private String id;

	@Id
	// @Column(table="table_l")
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

	private String lthree;

	@Basic
	@Column(table = "table_l")
	public String getLthree() {
		return lthree;
	}

	public void setLthree(String lthree) {
		this.lthree = lthree;
	}

	@Override
	public boolean equals(Object obj) {
		return equals(null, null, obj, JAXBEqualsStrategy.INSTANCE);
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {

		if (!(object instanceof L)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final L that = (L) object;
		return strategy.equals(null, null, this.getId(), that.getId())
				&& strategy.equals(null, null, this.getOne(), that.getOne())
				&& strategy.equals(null, null, this.getTwo(), that.getTwo())
				&& strategy.equals(null, null, this.getLthree(),
						that.getLthree());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
