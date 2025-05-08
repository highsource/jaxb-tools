package org.jvnet.jaxb.plugin.tostring;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.glassfish.jaxb.runtime.v2.ContextFactory;

import javax.xml.namespace.QName;

public class Customizations {

	public static final String NAMESPACE_URI = "urn:jaxb.jvnet.org:plugin:toString";

	public static QName IGNORED_ELEMENT_NAME = new QName(NAMESPACE_URI, "ignored");
    public static QName MASKED_ELEMENT_NAME = new QName(NAMESPACE_URI, "masked");
    public static final QName DATE_FORMAT_PATTERN = new QName(NAMESPACE_URI, "date-format");

    private static final JAXBContext context;
    static {
        try {
            context = ContextFactory.createContext(
                ObjectFactory.class.getPackage().getName(),
                ObjectFactory.class.getClassLoader(),
                null);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static JAXBContext getContext() {
        return context;
    }
}
