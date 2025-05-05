package org.jvnet.hyperjaxb3.ejb;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.ModelAndOutlineProcessor;

public interface IApplicationContext {
    ModelAndOutlineProcessor<EjbPlugin> getModelAndOutlineProcessor(String variant);

    Naming getNaming();

    Mapping getMapping();
}
