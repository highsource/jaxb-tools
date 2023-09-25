package org.jvnet.hyperjaxb3.lang.builder;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.lang3.ObjectUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.w3c.dom.Node;

public class ExtendedJAXBEqualsStrategy extends
		org.jvnet.jaxb.lang.ExtendedJAXBEqualsStrategy {


	@Override
	public boolean equals(ObjectLocator leftLocator,
						  ObjectLocator rightLocator, Object left, Object right,
						  boolean leftSet, boolean rightSet) {
		// do not check leftSet === rightSet equality since default attributes looked different
		return equals(leftLocator, rightLocator, left, right);
	}

	@Override
	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Node lhs, Node rhs) {
		final Diff diff = new Diff(new DOMSource(lhs), new DOMSource(rhs)) {
			@Override
			public int differenceFound(Difference difference) {
				if (difference.getId() == DifferenceConstants.NAMESPACE_PREFIX_ID) {
					return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
				} else {
					return super.differenceFound(difference);
				}
			}
		};
		return diff.identical();
	}

	@Override
	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, XMLGregorianCalendar left,
			XMLGregorianCalendar right) {
		return equals(leftLocator, rightLocator, XMLGregorianCalendarUtils
				.getTimeInMillis(left), XMLGregorianCalendarUtils
				.getTimeInMillis(right));
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Comparable<Object> left,
			Comparable<Object> right) {
		return ((Comparable<Object>) left).compareTo(right) == 0;
	}

	@Override
	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		if (lhs instanceof Comparable<?> && rhs instanceof Comparable<?>
				&& ObjectUtils.equals(lhs.getClass(), rhs.getClass())) {
			return equalsInternal(leftLocator, rightLocator,
					(Comparable<Object>) lhs, (Comparable<Object>) rhs);

		} else {
			return super.equalsInternal(leftLocator, rightLocator, lhs, rhs);
		}
	}
}
