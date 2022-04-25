package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;
import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.property;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class JAXBEqualsStrategy extends DefaultEqualsStrategy {

	@Override
	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right) {
		if (left instanceof JAXBElement<?> && right instanceof JAXBElement<?>) {
			return equalsInternal(leftLocator, rightLocator,
					(JAXBElement<?>) left, (JAXBElement<?>) right);
		} else if (left instanceof List<?> && right instanceof List<?>) {
			return equalsInternal(leftLocator, rightLocator, (List<?>) left,
					(List<?>) right);
		} else {
			return super.equalsInternal(leftLocator, rightLocator, left, right);

		}
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, final List<?> left, final List<?> right) {
		final Iterator<?> e1 = left.iterator();
		final Iterator<?> e2 = right.iterator();
		int index = 0;
		while (e1.hasNext() && e2.hasNext()) {
			Object o1 = e1.next();
			Object o2 = e2.next();
			if (!(o1 == null ? o2 == null : equals(
					item(leftLocator, index, o1),
					item(rightLocator, index, o2), o1, o2))) {
				return false;
			}
			index = index + 1;
		}
		return !(e1.hasNext() || e2.hasNext());
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, final JAXBElement<?> left,
			final JAXBElement<?> right) {
		return
		//
		equals(property(leftLocator, "name", left.getName()),
				property(rightLocator, "name", right.getName()),
				left.getName(), right.getName()) &&
		//
				equals(property(leftLocator, "value", left.getValue()),
						property(rightLocator, "name", right.getValue()),
						left.getValue(), right.getValue());
	}

	public static JAXBEqualsStrategy INSTANCE2 = new JAXBEqualsStrategy();
	@SuppressWarnings("deprecation")
	public static EqualsStrategy INSTANCE = INSTANCE2;
	
	public static JAXBEqualsStrategy getInstance() {
		return INSTANCE2;
	}
}