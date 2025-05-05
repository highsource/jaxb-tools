package org.jvnet.hyperjaxb3.ejb.jpa3.plugin;

import jakarta.xml.ns.persistence.orm.EmbeddableAttributes;
import org.jvnet.hyperjaxb3.ejb.jpa3.strategy.mapping.EmbeddableAttributesMapping;
import org.jvnet.hyperjaxb3.ejb.jpa3.strategy.mapping.MarshalMappings;
import org.jvnet.hyperjaxb3.ejb.jpa3.strategy.model.base.WrapCollectionBuiltinNonReference;
import org.jvnet.hyperjaxb3.ejb.jpa3.strategy.model.base.WrapCollectionEnumNonReference;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.ClassOutlineMapping;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller;

public class ApplicationContext extends org.jvnet.hyperjaxb3.ejb.plugin.ApplicationContext {

    @Override
    public ClassOutlineMapping<EmbeddableAttributes> createEmbeddableAttributesMapping() {
        return new EmbeddableAttributesMapping();
    }

    @Override
    public OutlineProcessor<EjbPlugin> createMarshalMappings(Ignoring ignoring, Mapping mapping) {
        MarshalMappings marshalMappings = new MarshalMappings();
        marshalMappings.setIgnoring(ignoring);
        marshalMappings.setMapping(mapping);
        return marshalMappings;
    }

    @Override
    public PersistenceFactory createPersistenceFactory() {
        return new org.jvnet.hyperjaxb3.ejb.jpa3.strategy.processor.PersistenceFactory();
    }

    @Override
    public PersistenceMarshaller createPersistenceMarshaller() {
        return new org.jvnet.hyperjaxb3.ejb.jpa3.strategy.processor.PersistenceMarshaller();
    }

    @Override
    public CreateXAnnotations createCreateXAnnotations() {
        return new org.jvnet.hyperjaxb3.ejb.jpa3.strategy.annotate.CreateXAnnotations();
    }

    @Override
    public ProcessModel customizeProcessModel(ProcessModel processModel) {
        ProcessModel customizedProcessModel = super.customizeProcessModel(processModel);
        if (customizedProcessModel instanceof DefaultProcessModel) {
            DefaultProcessModel defaultProcessModel = (DefaultProcessModel) customizedProcessModel;
            defaultProcessModel.setWrapCollectionBuiltinAttribute(new WrapCollectionBuiltinNonReference(defaultProcessModel.getWrapCollectionBuiltinAttribute()));
            defaultProcessModel.setWrapCollectionEnumAttribute(new WrapCollectionEnumNonReference());
            defaultProcessModel.setWrapCollectionBuiltinValue(new WrapCollectionBuiltinNonReference(defaultProcessModel.getWrapCollectionBuiltinValue()));
            defaultProcessModel.setWrapCollectionEnumValue(new WrapCollectionEnumNonReference());
        }
        return customizedProcessModel;
    }
}
