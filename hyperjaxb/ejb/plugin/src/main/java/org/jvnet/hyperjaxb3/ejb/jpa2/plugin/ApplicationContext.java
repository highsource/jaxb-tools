package org.jvnet.hyperjaxb3.ejb.jpa2.plugin;

import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import org.jvnet.hyperjaxb3.ejb.jpa2.strategy.mapping.EmbeddableAttributesMapping;
import org.jvnet.hyperjaxb3.ejb.jpa2.strategy.mapping.MarshalMappings;
import org.jvnet.hyperjaxb3.ejb.jpa2.strategy.model.base.WrapCollectionBuiltinNonReference;
import org.jvnet.hyperjaxb3.ejb.jpa2.strategy.model.base.WrapCollectionEnumNonReference;
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
        if (getDelegate() != null) {
            ClassOutlineMapping<EmbeddableAttributes> delegateResult = getDelegate().createEmbeddableAttributesMapping();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new EmbeddableAttributesMapping();
    }

    @Override
    public OutlineProcessor<EjbPlugin> createMarshalMappings(Ignoring ignoring, Mapping mapping) {
        if (getDelegate() != null) {
            OutlineProcessor<EjbPlugin> delegateResult = getDelegate().createMarshalMappings(ignoring, mapping);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        MarshalMappings marshalMappings = new MarshalMappings();
        marshalMappings.setIgnoring(ignoring);
        marshalMappings.setMapping(mapping);
        return marshalMappings;
    }

    @Override
    public PersistenceFactory createPersistenceFactory() {
        if (getDelegate() != null) {
            PersistenceFactory delegateResult = getDelegate().createPersistenceFactory();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new org.jvnet.hyperjaxb3.ejb.jpa2.strategy.processor.PersistenceFactory();
    }

    @Override
    public PersistenceMarshaller createPersistenceMarshaller() {
        if (getDelegate() != null) {
            PersistenceMarshaller delegateResult = getDelegate().createPersistenceMarshaller();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new org.jvnet.hyperjaxb3.ejb.jpa2.strategy.processor.PersistenceMarshaller();
    }

    @Override
    public CreateXAnnotations createCreateXAnnotations() {
        if (getDelegate() != null) {
            CreateXAnnotations delegateResult = getDelegate().createCreateXAnnotations();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new org.jvnet.hyperjaxb3.ejb.jpa2.strategy.annotate.CreateXAnnotations();
    }

    @Override
    public ProcessModel customizeProcessModel(ProcessModel processModel) {
        // If not, use parent class implementation and then apply JPA3-specific customizations
        ProcessModel customizedProcessModel = super.customizeProcessModel(processModel);
        if (customizedProcessModel instanceof DefaultProcessModel) {
            DefaultProcessModel defaultProcessModel = (DefaultProcessModel) customizedProcessModel;
            defaultProcessModel.setWrapCollectionBuiltinAttribute(new WrapCollectionBuiltinNonReference(defaultProcessModel.getWrapCollectionBuiltinAttribute()));
            defaultProcessModel.setWrapCollectionEnumAttribute(new WrapCollectionEnumNonReference());
            defaultProcessModel.setWrapCollectionBuiltinElement(new WrapCollectionBuiltinNonReference(defaultProcessModel.getWrapCollectionBuiltinElement()));
            defaultProcessModel.setWrapCollectionEnumElement(new WrapCollectionEnumNonReference());
            defaultProcessModel.setWrapCollectionBuiltinValue(new WrapCollectionBuiltinNonReference(defaultProcessModel.getWrapCollectionBuiltinValue()));
            defaultProcessModel.setWrapCollectionEnumValue(new WrapCollectionEnumNonReference());
        }
        return customizedProcessModel;
    }
}
