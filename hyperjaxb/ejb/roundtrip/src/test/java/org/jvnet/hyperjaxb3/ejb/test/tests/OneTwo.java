package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@XmlType(name = "OneTwo", propOrder = { "one", "two" })
@Entity
@Table(name = "table_onetwo")
public class OneTwo implements Equals {
	
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * Sets the value of the hjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
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

	@Override
	public boolean equals(Object obj) {
		return equals(null, null, obj, JAXBEqualsStrategy.INSTANCE);
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {

		if (!(object instanceof OneTwo)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final OneTwo that = (OneTwo) object;
		return strategy.equals(null, null, this.getOne(), that.getOne())
				&& strategy.equals(null, null, this.getTwo(), that.getTwo());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
