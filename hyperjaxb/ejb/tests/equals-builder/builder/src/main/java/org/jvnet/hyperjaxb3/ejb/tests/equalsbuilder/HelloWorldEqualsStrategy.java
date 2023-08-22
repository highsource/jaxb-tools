package org.jvnet.hyperjaxb3.ejb.tests.equalsbuilder;

import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class HelloWorldEqualsStrategy extends JAXBEqualsStrategy {

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		System.out.println("Hello world!");
		return super.equals(leftLocator, rightLocator, lhs, rhs);
	}

}
