package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "M", namespace = "")
@XmlType(name = "M", propOrder = { "id", "one", "two", "mthree" })
@Entity
@Table(name = "table_m")
public class M implements Equals {

	private String id;

	@Id
	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getOne() {
		if (getOneTwo() == null) {
			setOneTwo(new OneTwo());
		}
		return getOneTwo().getOne();
	}

	public void setOne(String one) {
		if (getOneTwo() == null) {
			setOneTwo(new OneTwo());
		}
		getOneTwo().setOne(one);
	}

	@Transient
	public String getTwo() {
		if (getOneTwo() == null) {
			setOneTwo(new OneTwo());
		}
		return getOneTwo().getTwo();
	}

	public void setTwo(String two) {
		if (getOneTwo() == null) {
			setOneTwo(new OneTwo());
		}
		getOneTwo().setTwo(two);
	}

	private OneTwo oneTwo;

	@ManyToOne(targetEntity = OneTwo.class, cascade = { CascadeType.ALL })
	@XmlTransient
	public OneTwo getOneTwo() {
		return oneTwo;
	}

	public void setOneTwo(OneTwo oneTwo) {
		this.oneTwo = oneTwo;
	}

	private String mthree;

	@Basic
	public String getMthree() {
		return mthree;
	}

	public void setMthree(String mthree) {
		this.mthree = mthree;
	}

	@Override
	public boolean equals(Object obj) {
		return equals(null, null, obj, JAXBEqualsStrategy.INSTANCE);
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {

		if (!(object instanceof M)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final M that = (M) object;
		return strategy.equals(null, null, this.getId(), that.getId())
				&& strategy.equals(null, null, this.getOne(), that.getOne())
				&& strategy.equals(null, null, this.getTwo(), that.getTwo())
				&& strategy.equals(null, null, this.getMthree(),
						that.getMthree());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
