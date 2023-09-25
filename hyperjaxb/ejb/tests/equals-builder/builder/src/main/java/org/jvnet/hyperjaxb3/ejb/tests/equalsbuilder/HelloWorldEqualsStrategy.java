package org.jvnet.hyperjaxb3.ejb.tests.equalsbuilder;

import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;

public class HelloWorldEqualsStrategy extends JAXBEqualsStrategy {

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		System.out.println("Hello world!");
		return super.equals(leftLocator, rightLocator, lhs, rhs);
	}

}
