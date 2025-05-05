package org.jvnet.hyperjaxb3.beans.factory;

import java.util.Map;

import jakarta.xml.bind.JAXBContext;

public class JAXBContextFactory {

	public static JAXBContext createInstance(String contextPath) throws Exception {
        return JAXBContext.newInstance(contextPath);
    }

    public static JAXBContext createInstance(String contextPath, ClassLoader classLoader) throws Exception {
        return JAXBContext.newInstance(contextPath, classLoader);
    }

    public static JAXBContext createInstance(String contextPath, Map<String, ?> properties) throws Exception {
        return JAXBContext.newInstance(contextPath, Thread.currentThread().getContextClassLoader(), properties);
    }

    public static JAXBContext createInstance(String contextPath, ClassLoader classLoader, Map<String, ?> properties) throws Exception {
        return JAXBContext.newInstance(contextPath, classLoader, properties);
    }
}
