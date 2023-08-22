package org.jvnet.hyperjaxb3.beans.factory;

import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class JAXBContextFactoryBean extends AbstractFactoryBean {

	private String contextPath;

	public String getContextPath() {
		return contextPath;
	}

	@Required
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	private ClassLoader classLoader;

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	private Map<String, ?> properties;

	public Map<String, ?> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, ?> properties) {
		this.properties = properties;
	}

	@Override
	protected Object createInstance() throws Exception {
		if (getClassLoader() == null && getProperties() == null) {
			return JAXBContext.newInstance(getContextPath());
		} else if (getClassLoader() != null && getProperties() == null) {
			return JAXBContext.newInstance(getContextPath(), getClassLoader());
		} else if (getClassLoader() == null && getProperties() != null) {
			return JAXBContext.newInstance(getContextPath(), Thread
					.currentThread().getContextClassLoader(), getProperties());
		} else {
			return JAXBContext.newInstance(getContextPath(), getClassLoader(),
					getProperties());
		}
	}

	@Override
	public Class<?> getObjectType() {
		return JAXBContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
