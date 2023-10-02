package org.jvnet.hyperjaxb3.ejb;

import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

public class Constants {

    public static final String NAMESPACE = "urn:hyperjaxb3.jvnet.org";

	public static final Locator EMPTY_LOCATOR;

	static {
		LocatorImpl l = new LocatorImpl();
		l.setColumnNumber(-1);
		l.setLineNumber(-1);
		EMPTY_LOCATOR = l;
	}

}
