package org.jvnet.hyperjaxb3.ejb.extensions.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

public class CustomApplicationContext extends org.jvnet.hyperjaxb3.ejb.plugin.ApplicationContext {

    @Override
    public DefaultNaming createNaming(Ignoring ignoring) {
        CustomSimpleNoUnderscoreNaming naming = new CustomSimpleNoUnderscoreNaming();
        naming.setIgnoring(ignoring);
        naming.setReservedNames(createReservedNames());
        naming.afterPropertiesSet();
        return naming;
    }
}
