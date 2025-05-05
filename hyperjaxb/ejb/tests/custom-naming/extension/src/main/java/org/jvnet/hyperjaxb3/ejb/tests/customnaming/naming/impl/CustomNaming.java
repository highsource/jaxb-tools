package org.jvnet.hyperjaxb3.ejb.tests.customnaming.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

import java.util.Properties;

public class CustomNaming extends DefaultNaming {

    public CustomNaming(Ignoring ignoring, Properties reservedNames) {
        super(ignoring, reservedNames);
    }

    @Override
    public String getName(Mapping context, String draftName) {
        return "FOO_" + super.getName(context, draftName);
    }

}
