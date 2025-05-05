package org.jvnet.hyperjaxb3.ejb.extensions.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

import java.util.Properties;

public class CustomSimpleNoUnderscoreNaming extends DefaultNaming {

    public CustomSimpleNoUnderscoreNaming(Ignoring ignoring, Properties reservedNames) {
        super(ignoring, reservedNames);
    }

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
