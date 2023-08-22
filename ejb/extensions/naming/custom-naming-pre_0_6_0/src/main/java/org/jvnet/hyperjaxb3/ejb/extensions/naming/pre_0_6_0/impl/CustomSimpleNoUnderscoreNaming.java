package org.jvnet.hyperjaxb3.ejb.extensions.naming.pre_0_6_0.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

public class CustomSimpleNoUnderscoreNaming extends DefaultNaming {
	
	@Override
	public String getName(Mapping context, final String draftName) {
		String name = super.getName(context, draftName);
		if (name.startsWith("_")){
			return "_" + name.replace("_", "");
		} else {
			return name.replace("_", "");
		}
	}


}
