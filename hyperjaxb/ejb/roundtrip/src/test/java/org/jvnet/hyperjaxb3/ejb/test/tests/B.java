package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
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
@XmlType(name = "B", propOrder = { "id", "version", "c" })
@Entity
@Table(name = "table_b")
public class B implements Equals {

	@XmlAttribute
	private String id;

	private String c;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getC() {
		return c;
	}

	public void setC(String value) {
		this.c = value;
	}

	@XmlAttribute
	private int version;

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		return equals(null, null, obj, JAXBEqualsStrategy.INSTANCE);
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {

		if (!(object instanceof B)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final B that = (B) object;
		return strategy.equals(null, null, this.getId(), that.getId())
				&& strategy.equals(null, null, this.getC(), that.getC());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}