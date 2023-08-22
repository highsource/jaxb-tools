package org.jvnet.hyperjaxb3.ejb.tests.customnaming.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

public class CustomNaming extends DefaultNaming {

	@Override
	public String getName(String draftName) {
		return "FOO_" + super.getName(draftName);
	}

}
