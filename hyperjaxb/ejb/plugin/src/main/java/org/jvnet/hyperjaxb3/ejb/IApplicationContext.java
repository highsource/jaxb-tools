package org.jvnet.hyperjaxb3.ejb;

import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.AnnotateOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.ClassOutlineMapping;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.ModelAndOutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;

import javax.xml.bind.JAXBContext;
import java.util.Properties;

public interface IApplicationContext {
    default void setDelegate(IApplicationContext delegate) {
    }
    default JAXBContext getFactory() {
        return null;
    }
    default Customizing getCustomizing() {
        return null;
    }
    default Ignoring getIgnoring() {
        return null;
    }
    default ModelAndOutlineProcessor<EjbPlugin> getModelAndOutlineProcessor(String variant) {
        return null;
    }
    default Naming getNaming() {
        return null;
    }
    default Mapping getMapping() {
        return null;
    }

    default ClassOutlineMapping<EmbeddableAttributes> createEmbeddableAttributesMapping() {
        return null;
    }
    default ProcessModel createProcessModel(Customizing customizing, Ignoring ignoring) {
        return null;
    }
    default ProcessModel customizeProcessModel(ProcessModel processModel) {
        // nothing to do by default
        return processModel;
    }
    default AnnotateOutline createAnnotateOutline(Ignoring ignoring, Mapping mapping) {
        return null;
    }
    default CreateXAnnotations createCreateXAnnotations() {
        return null;
    }
    default Mapping createMapping(Customizing customizing, Naming naming, Ignoring ignoring, ClassOutlineMapping<EmbeddableAttributes> embeddableAttributesMapping) {
        return null;
    }
    default Customizing createCustomizing() throws Exception {
        return null;
    }
    default Ignoring createIgnoring(Customizing customizing) {
        return null;
    }
    default Naming createNaming(Ignoring ignoring, Properties reservedNames) {
        return null;
    }
    default Properties createReservedNames() {
        return null;
    }
    default OutlineProcessor<EjbPlugin> createAnnotationsProcessor(AnnotateOutline annotateOutline, Naming naming) {
        return null;
    }
    default PersistenceFactory createPersistenceFactory() {
        return null;
    }
    default PersistenceMarshaller createPersistenceMarshaller() {
        return null;
    }
    default OutlineProcessor<EjbPlugin> createMarshalMappings(Ignoring ignoring, Mapping mapping) {
        return null;
    }
    default OutlineProcessor<EjbPlugin> createMappingFilesProcessor(OutlineProcessor<EjbPlugin> marshalMappings, Naming naming) {
        return null;
    }
}
