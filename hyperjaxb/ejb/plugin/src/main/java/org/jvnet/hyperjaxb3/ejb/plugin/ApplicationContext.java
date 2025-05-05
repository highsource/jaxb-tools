package org.jvnet.hyperjaxb3.ejb.plugin;

import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import org.jvnet.hyperjaxb3.beans.factory.JAXBContextFactory;
import org.jvnet.hyperjaxb3.beans.factory.UnmarshalledResourceFactory;
import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.AnnotateOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.impl.DefaultCustomizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.impl.DefaultIgnoring;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.ClassOutlineMapping;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.EmbeddableAttributesMapping;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.MarshalMappings;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.AdaptBuiltinTypeUse;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.CreateIdClass;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultCreateDefaultIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultCreateDefaultVersionPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultGetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultProcessPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.GetIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.GetVersionPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapCollectionAttribute;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapCollectionElement;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapCollectionHeteroElement;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapCollectionHeteroReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapCollectionValue;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleBuiltinNonReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleBuiltinReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleClassElementReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleEnumElementReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleEnumNonReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleHeteroElement;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleHeteroReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleSubstitutedElementReference;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.WrapSingleWildcardReference;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.ClassPersistenceProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.MappingFilePersistenceProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.ModelAndOutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.DefaultModelAndOutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller;

import javax.xml.bind.JAXBContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class ApplicationContext implements IApplicationContext {

    private IApplicationContext delegate = null;
    private JAXBContext factory;
    private Ignoring ignoring;
    private Customizing customizing;
    private Naming naming;
    private Mapping mapping;

    public IApplicationContext getDelegate() {
        return delegate;
    }

    public void setDelegate(IApplicationContext delegate) {
        this.delegate = delegate;
    }

    public JAXBContext getFactory() {
        if (factory == null) {
            if (delegate != null) {
                factory = delegate.getFactory();
            }
        }
        if (factory == null) {
            try {
                factory = JAXBContextFactory.createInstance(
                    "com.sun.java.xml.ns.persistence:com.sun.java.xml.ns.persistence.orm:org.jvnet.hyperjaxb3.ejb.schemas.customizations");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return factory;
    }

    public Customizing getCustomizing() {
        if (customizing == null) {
            if (delegate != null) {
                customizing = delegate.getCustomizing();
            }
        }
        if (customizing == null) {
            try {
                customizing = createCustomizing();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customizing;
    }

    public Ignoring getIgnoring() {
        if (ignoring == null) {
            if (delegate != null) {
                ignoring = delegate.getIgnoring();
            }
        }
        if (ignoring == null) {
            ignoring = createIgnoring(getCustomizing());
        }
        return ignoring;
    }

    @Override
    public ModelAndOutlineProcessor<EjbPlugin> getModelAndOutlineProcessor(String variant) {
        if (delegate != null) {
            ModelAndOutlineProcessor<EjbPlugin> delegateResult = delegate.getModelAndOutlineProcessor(variant);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        if ("mappingFiles".equalsIgnoreCase(variant)) {
            DefaultModelAndOutlineProcessor mappingFiles = new DefaultModelAndOutlineProcessor();
            mappingFiles.setModelProcessor(createProcessModel(getCustomizing(), getIgnoring()));
            mappingFiles.setOutlineProcessor(createMappingFilesProcessor(createMarshalMappings(getIgnoring(), getMapping()), getNaming()));
            return mappingFiles;
        }
        DefaultModelAndOutlineProcessor annotations = new DefaultModelAndOutlineProcessor();
        annotations.setModelProcessor(createProcessModel(getCustomizing(), getIgnoring()));
        annotations.setOutlineProcessor(createAnnotationsProcessor(createAnnotateOutline(getIgnoring(), getMapping()), getNaming()));
        return annotations;
    }

    @Override
    public Naming getNaming() {
        if (naming == null) {
            if (delegate != null) {
                naming = delegate.getNaming();
            }
        }
        if (naming == null) {
            naming = createNaming(getIgnoring(), createReservedNames());
        }
        return naming;
    }

    @Override
    public Mapping getMapping() {
        if (mapping == null) {
            if (delegate != null) {
                mapping = delegate.getMapping();
            }
        }
        if (mapping == null) {
            mapping = createMapping(getCustomizing(), getNaming(), getIgnoring(), createEmbeddableAttributesMapping());
        }
        return mapping;
    }

    public ClassOutlineMapping<EmbeddableAttributes> createEmbeddableAttributesMapping() {
        if (delegate != null) {
            ClassOutlineMapping<EmbeddableAttributes> delegateResult = delegate.createEmbeddableAttributesMapping();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new EmbeddableAttributesMapping();
    }

    public ProcessModel createProcessModel(Customizing customizing, Ignoring ignoring) {
        if (delegate != null) {
            ProcessModel delegateResult = delegate.createProcessModel(customizing, ignoring);
            if (delegateResult != null) {
                return customizeProcessModel(delegateResult);
            }
        }
        DefaultProcessModel processModel = new DefaultProcessModel();
        processModel.setGetTypes(new DefaultGetTypes<>());
        processModel.setProcessClassInfo(new DefaultProcessClassInfo());
        processModel.setProcessPropertyInfos(new DefaultProcessPropertyInfos());
        processModel.setCreateIdClass(new CreateIdClass());
        processModel.setCreateDefaultIdPropertyInfos(new DefaultCreateDefaultIdPropertyInfos());
        processModel.setCreateDefaultVersionPropertyInfos(new DefaultCreateDefaultVersionPropertyInfos());
        processModel.setGetIdPropertyInfos(new GetIdPropertyInfos());
        processModel.setGetVersionPropertyInfos(new GetVersionPropertyInfos());
        processModel.setAdaptBuiltinTypeUse(new AdaptBuiltinTypeUse());
        processModel.setCustomizing(customizing);
        processModel.setIgnoring(ignoring);

        WrapSingleBuiltinNonReference wrapSingleBuiltin = new WrapSingleBuiltinNonReference();
        processModel.setWrapSingleBuiltinAttribute(wrapSingleBuiltin);
        processModel.setWrapSingleBuiltinValue(wrapSingleBuiltin);
        processModel.setWrapSingleBuiltinElement(wrapSingleBuiltin);

        WrapSingleEnumNonReference wrapSingleEnum = new WrapSingleEnumNonReference();
        processModel.setWrapSingleEnumAttribute(wrapSingleEnum);
        processModel.setWrapSingleEnumValue(wrapSingleEnum);
        processModel.setWrapSingleEnumElement(wrapSingleEnum);

        processModel.setWrapCollectionBuiltinAttribute(new WrapCollectionAttribute());
        processModel.setWrapCollectionEnumAttribute(new WrapCollectionAttribute());

        processModel.setWrapCollectionBuiltinValue(new WrapCollectionValue());
        processModel.setWrapCollectionEnumValue(new WrapCollectionValue());

        processModel.setWrapSingleHeteroElement(new WrapSingleHeteroElement());
        processModel.setWrapCollectionBuiltinElement(new WrapCollectionElement());
        processModel.setWrapCollectionEnumElement(new WrapCollectionElement());
        processModel.setWrapCollectionHeteroElement(new WrapCollectionHeteroElement());

        processModel.setWrapSingleBuiltinElementReference(new WrapSingleBuiltinReference());
        processModel.setWrapSingleEnumElementReference(new WrapSingleEnumElementReference());
        processModel.setWrapSingleClassElementReference(new WrapSingleClassElementReference());
        processModel.setWrapSingleSubstitutedElementReference(new WrapSingleSubstitutedElementReference());
        processModel.setWrapSingleClassReference(new WrapSingleHeteroReference());
        processModel.setWrapSingleHeteroReference(new WrapSingleHeteroReference());
        processModel.setWrapSingleWildcardReference(new WrapSingleWildcardReference());
        processModel.setWrapCollectionHeteroReference(new WrapCollectionHeteroReference());
        processModel.setWrapCollectionWildcardReference(new WrapCollectionHeteroReference());
        return customizeProcessModel(processModel);
    }

    public ProcessModel customizeProcessModel(ProcessModel processModel) {
        if (delegate != null) {
            return delegate.customizeProcessModel(processModel);
        }
        return processModel;
    }

    public AnnotateOutline createAnnotateOutline(Ignoring ignoring, Mapping mapping) {
        if (delegate != null) {
            AnnotateOutline delegateResult = delegate.createAnnotateOutline(ignoring, mapping);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        AnnotateOutline annotateOutline = new AnnotateOutline();
        annotateOutline.setMapping(mapping);
        annotateOutline.setIgnoring(ignoring);
        annotateOutline.setCreateXAnnotations(createCreateXAnnotations());
        return annotateOutline;
    }

    public CreateXAnnotations createCreateXAnnotations() {
        if (delegate != null) {
            CreateXAnnotations delegateResult = delegate.createCreateXAnnotations();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new CreateXAnnotations();
    }

    public Mapping createMapping(Customizing customizing, Naming naming, Ignoring ignoring, ClassOutlineMapping<EmbeddableAttributes> embeddableAttributesMapping) {
        if (delegate != null) {
            Mapping delegateResult = delegate.createMapping(customizing, naming, ignoring, embeddableAttributesMapping);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        Mapping mapping = new Mapping();
        mapping.setGetTypes(new DefaultGetTypes<>());
        mapping.setCustomizing(customizing);
        mapping.setIgnoring(ignoring);
        mapping.setNaming(naming);
        mapping.setEmbeddableAttributesMapping(embeddableAttributesMapping);
        return mapping;
    }

    public Customizing createCustomizing() throws Exception {
        if (delegate != null) {
            Customizing delegateResult = delegate.createCustomizing();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        DefaultCustomizing customizing = new DefaultCustomizing();
        URL url = this.getClass().getClassLoader().getResource("org/jvnet/hyperjaxb3/ejb/strategy/customizing/impl/DefaultCustomizations.xml");
        if (url != null) {
            try (InputStream is = url.openStream()) {
                customizing.setDefaultCustomizations((Persistence) UnmarshalledResourceFactory.createInstance(is, url.toString(), getFactory()));
            }
        }
        return customizing;
    }

    public DefaultIgnoring createIgnoring(Customizing customizing) {
        if (delegate != null) {
            Ignoring delegateResult = delegate.createIgnoring(customizing);
            if (delegateResult != null) {
                return (DefaultIgnoring) delegateResult;
            }
        }
        DefaultIgnoring ignoring = new DefaultIgnoring();
        ignoring.setCustomizing(customizing);
        return ignoring;
    }

    public DefaultNaming createNaming(Ignoring ignoring, Properties reservedNames) {
        if (delegate != null) {
            Naming delegateResult = delegate.createNaming(ignoring, reservedNames);
            if (delegateResult != null) {
                return (DefaultNaming) delegateResult;
            }
        }
        return new DefaultNaming(ignoring, reservedNames);
    }

    public Properties createReservedNames() {
        if (delegate != null) {
            Properties delegateResult = delegate.createReservedNames();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        try {
            Properties properties = new Properties();
            Enumeration<URL> resourcesEnum = this.getClass().getClassLoader().getResources("org/jvnet/hyperjaxb3/ejb/strategy/naming/impl/ReservedNames.properties");
            Iterator<URL> urls = Collections.list(resourcesEnum).iterator();
            while (urls.hasNext()) {
                try (InputStream stream = urls.next().openStream()) {
                    properties.load(stream);
                }
            }
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OutlineProcessor<EjbPlugin> createAnnotationsProcessor(AnnotateOutline annotateOutline, Naming naming) {
        if (delegate != null) {
            OutlineProcessor<EjbPlugin> delegateResult = delegate.createAnnotationsProcessor(annotateOutline, naming);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        ClassPersistenceProcessor annotationsProcessor = new ClassPersistenceProcessor();
        annotationsProcessor.setOutlineProcessor(annotateOutline);
        annotationsProcessor.setNaming(naming);
        annotationsProcessor.setPersistenceFactory(createPersistenceFactory());
        annotationsProcessor.setPersistenceMarshaller(createPersistenceMarshaller());
        return annotationsProcessor;
    }

    public PersistenceFactory createPersistenceFactory() {
        if (delegate != null) {
            PersistenceFactory delegateResult = delegate.createPersistenceFactory();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new PersistenceFactory();
    }

    public PersistenceMarshaller createPersistenceMarshaller() {
        if (delegate != null) {
            PersistenceMarshaller delegateResult = delegate.createPersistenceMarshaller();
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        return new PersistenceMarshaller();
    }

    public OutlineProcessor<EjbPlugin> createMarshalMappings(Ignoring ignoring, Mapping mapping) {
        if (delegate != null) {
            OutlineProcessor<EjbPlugin> delegateResult = delegate.createMarshalMappings(ignoring, mapping);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        MarshalMappings marshalMappings = new MarshalMappings();
        marshalMappings.setIgnoring(ignoring);
        marshalMappings.setMapping(mapping);
        return marshalMappings;
    }

    public OutlineProcessor<EjbPlugin> createMappingFilesProcessor(OutlineProcessor<EjbPlugin> marshalMappings, Naming naming) {
        if (delegate != null) {
            OutlineProcessor<EjbPlugin> delegateResult = delegate.createMappingFilesProcessor(marshalMappings, naming);
            if (delegateResult != null) {
                return delegateResult;
            }
        }
        MappingFilePersistenceProcessor mappingFilesProcessor = new MappingFilePersistenceProcessor();
        mappingFilesProcessor.setOutlineProcessor(marshalMappings);
        mappingFilesProcessor.setNaming(naming);
        mappingFilesProcessor.setPersistenceFactory(createPersistenceFactory());
        mappingFilesProcessor.setPersistenceMarshaller(createPersistenceMarshaller());
        return mappingFilesProcessor;
    }
}
