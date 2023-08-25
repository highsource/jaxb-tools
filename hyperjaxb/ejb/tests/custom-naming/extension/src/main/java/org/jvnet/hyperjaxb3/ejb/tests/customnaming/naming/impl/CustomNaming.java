package org.jvnet.hyperjaxb3.ejb.tests.customnaming.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

public class CustomNaming extends DefaultNaming {

	@Override
	public String getName(Mapping context, String draftName) {
		return "FOO_" + super.getName(context, draftName);
	}

}
