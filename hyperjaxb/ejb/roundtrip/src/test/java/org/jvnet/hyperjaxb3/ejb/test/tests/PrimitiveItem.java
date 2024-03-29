package org.jvnet.hyperjaxb3.ejb.test.tests;

import jakarta.persistence.Basic;
import jakarta.persistence.MappedSuperclass;

import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.jaxb.lang.Equals;
import org.jvnet.jaxb.lang.EqualsStrategy;
import org.jvnet.jaxb.lang.HashCode;
import org.jvnet.jaxb.lang.HashCodeStrategy;
import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.locator.util.LocatorUtils;

@MappedSuperclass
public abstract class PrimitiveItem<T, V> implements Equals, HashCode, Item<V> {

	private T value;

	@Basic
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {
		if (!(object instanceof PrimitiveItem)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final PrimitiveItem that = ((PrimitiveItem) object);
		{
			Object lhsValue;
			lhsValue = this.getValue();
			Object rhsValue;
			rhsValue = that.getValue();
			if (!strategy.equals(LocatorUtils.property(thisLocator, "value",
					lhsValue), LocatorUtils.property(thatLocator, "value",
					rhsValue), lhsValue, rhsValue, this.value != null, that.value != null)) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(Object object) {
		final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
		return equals(null, null, object, strategy);
	}

	public int hashCode(ObjectLocator locator, HashCodeStrategy hashCodeStrategy) {

		final T theValue;
		theValue = this.getValue();
		return hashCodeStrategy.hashCode(LocatorUtils.property(locator, "value",
				theValue), 0, theValue, this.value != null);
	}

	public int hashCode() {
		return hashCode(null, JAXBHashCodeStrategy.INSTANCE);
	}

}
