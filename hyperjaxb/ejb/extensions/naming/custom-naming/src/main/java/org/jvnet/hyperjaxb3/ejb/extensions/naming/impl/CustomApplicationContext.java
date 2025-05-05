package org.jvnet.hyperjaxb3.ejb.extensions.naming.impl;

import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

import java.util.Properties;

public class CustomApplicationContext implements IApplicationContext {

    @Override
    public DefaultNaming createNaming(Ignoring ignoring, Properties reservedNames) {
        return new CustomSimpleNoUnderscoreNaming(ignoring, reservedNames);
    }
}
