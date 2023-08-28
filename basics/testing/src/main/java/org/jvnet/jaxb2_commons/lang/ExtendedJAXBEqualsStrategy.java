package org.jvnet.jaxb2_commons.lang;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMSource;

import org.custommonkey.xmlunit.Diff;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.w3c.dom.Node;

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
			ObjectLocator rightLocator, Node lhs, Node rhs) {
		final Diff diff = new Diff(new DOMSource((Node) lhs), new DOMSource(
				(Node) rhs));
		return diff.identical();
	}

}
