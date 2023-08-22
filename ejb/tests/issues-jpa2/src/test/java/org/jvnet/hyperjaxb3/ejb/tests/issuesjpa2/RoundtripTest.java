package org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2;

import org.jvnet.jaxb2_commons.xml.bind.ContextPathAware;

public class RoundtripTest extends org.jvnet.hyperjaxb3.ejb.test.RoundtripTest
		implements ContextPathAware {

	public String getContextPath() {
		return "org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2";
	}

	public String getPersistenceUnitName() {
		return "org.jvnet.hyperjaxb3.ejb.tests.issuesjpa2";
	}

}
