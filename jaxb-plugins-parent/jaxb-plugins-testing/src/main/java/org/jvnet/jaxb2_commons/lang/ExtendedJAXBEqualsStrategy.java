package org.jvnet.jaxb2_commons.lang;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMSource;

import org.custommonkey.xmlunit.Diff;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.util.Objects;

public class ExtendedJAXBEqualsStrategy extends JAXBEqualsStrategy {

	@Override
	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {

		if (lhs instanceof Node && rhs instanceof Node) {
			return equalsInternal(leftLocator, rightLocator, (Node) lhs,
					(Node) rhs);
		} else if (lhs instanceof XMLGregorianCalendar
				&& rhs instanceof XMLGregorianCalendar) {
			return equalsInternal(leftLocator, rightLocator,
					(XMLGregorianCalendar) lhs, (XMLGregorianCalendar) rhs);

		} else if (lhs instanceof BigDecimal
				&& rhs instanceof BigDecimal) {
			return equalsInternal(leftLocator, rightLocator,
					(BigDecimal) lhs, (BigDecimal) rhs);

		} else {
			return super.equalsInternal(leftLocator, rightLocator, lhs, rhs);
		}
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, XMLGregorianCalendar left,
			XMLGregorianCalendar right) {
		return equals(leftLocator, rightLocator, left.normalize()
				.toGregorianCalendar().getTimeInMillis(), right.normalize()
				.toGregorianCalendar().getTimeInMillis());
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
									 ObjectLocator rightLocator,
									 BigDecimal left,
									 BigDecimal right) {
		if (Objects.equals(left, right)) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		return left.compareTo(right) == 0;
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Node lhs, Node rhs) {
		final Diff diff = new Diff(new DOMSource((Node) lhs), new DOMSource(
				(Node) rhs));
		return diff.identical();
	}
	public static JAXBEqualsStrategy INSTANCE2 = new ExtendedJAXBEqualsStrategy();
	@SuppressWarnings("deprecation")
	public static EqualsStrategy INSTANCE = INSTANCE2;

	public static JAXBEqualsStrategy getInstance() {
		return INSTANCE2;
	}

}
