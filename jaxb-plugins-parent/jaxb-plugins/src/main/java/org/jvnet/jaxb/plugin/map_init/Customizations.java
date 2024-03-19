package org.jvnet.jaxb.plugin.map_init;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.glassfish.jaxb.runtime.v2.ContextFactory;
import org.jvnet.jaxb.util.CustomizationUtils;

import javax.xml.namespace.QName;

public class Customizations {

    public static String NAMESPACE_URI = "urn:jaxb.jvnet.org:plugin:mapinit";

    public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI, "ignored");

    public static QName OBJECT_FACTORY_ELEMENT_NAME = new QName(NAMESPACE_URI, "objectFactory");

    public static QName INIT_CLASS_ELEMENT_NAME = new QName(NAMESPACE_URI, "class");


    private static final JAXBContext context;
    static {
        try {
            context = ContextFactory.createContext(
                org.jvnet.jaxb.plugin.map_init.ObjectFactory.class.getPackage().getName(),
                ObjectFactory.class.getClassLoader(),
                null);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static JAXBContext getContext() {
        return context;
    }

    public static void _initClass(CClassInfo classInfo, String className) {
        final InitClass initClass = new InitClass();
        initClass.setClassName(className);
        final CPluginCustomization customization = CustomizationUtils.marshal(getContext(), Customizations.INIT_CLASS_ELEMENT_NAME, initClass);
        classInfo.getCustomizations().add(customization);
        customization.markAsAcknowledged();

    }
}
