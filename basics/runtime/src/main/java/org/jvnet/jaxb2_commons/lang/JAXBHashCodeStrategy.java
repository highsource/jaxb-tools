package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;
import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.property;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class JAXBHashCodeStrategy extends DefaultHashCodeStrategy {

	public JAXBHashCodeStrategy() {
		super();
	}

	public JAXBHashCodeStrategy(int multiplierNonZeroOddNumber) {
		super(multiplierNonZeroOddNumber);
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			Object value) {
		if (value instanceof JAXBElement<?>) {
			final JAXBElement<?> element = (JAXBElement<?>) value;
			return hashCodeInternal(locator, hashCode, element);
		} else if (value instanceof List<?>) {
			final List<?> list = (List<?>) value;
			return hashCodeInternal(locator, hashCode, list);
		} else {
			return super.hashCodeInternal(locator, hashCode, value);
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			final JAXBElement<?> element) {
		int currentHashCode = hashCode;
		currentHashCode = hashCode(
				property(locator, "name", element.getName()), currentHashCode,
				element.getName());
		currentHashCode = hashCode(
				property(locator, "declaredType", element.getDeclaredType()),
				currentHashCode, element.getDeclaredType());
		currentHashCode = hashCode(
				property(locator, "scope", element.getScope()),
				currentHashCode, element.getScope());
		currentHashCode = hashCode(
				property(locator, "value", element.getValue()),
				currentHashCode, element.getValue());
		return currentHashCode;
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			final List<?> list) {
		// Treat empty lists as nulls
		if (list.isEmpty()) {
			return super.hashCode(locator, hashCode, (Object) null);
		} else {
			int currentHashCode = hashCode;
			for (int index = 0; index < list.size(); index++) {
				final Object item = list.get(index);
				currentHashCode = hashCode(item(locator, index, item),
						currentHashCode, item);
			}
			return currentHashCode;
		}
	}

	public static JAXBHashCodeStrategy INSTANCE2 = new JAXBHashCodeStrategy();
	@SuppressWarnings("deprecation")
	public static HashCodeStrategy INSTANCE = INSTANCE2;

	public static JAXBHashCodeStrategy getInstance() {
		return INSTANCE2;
	}
}