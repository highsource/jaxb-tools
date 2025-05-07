package org.jvnet.hyperjaxb3.ejb.tests.customnaming.naming.impl;

import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

public class CustomApplicationContext extends org.jvnet.hyperjaxb3.ejb.plugin.ApplicationContext {

    @Override
    public DefaultNaming createNaming(Ignoring ignoring) {
        CustomNaming naming = new CustomNaming();
        naming.setIgnoring(ignoring);
        naming.setReservedNames(createReservedNames());
        naming.afterPropertiesSet();
        return naming;
    }
}
