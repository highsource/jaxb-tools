package org.jvnet.hyperjaxb3.beans.factory;

import java.io.IOException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

public class UnmarshalledResourceFactoryBean extends AbstractFactoryBean {

	private JAXBContext context;

	public JAXBContext getContext() {
		return context;
	}

	@Required
	public void setContext(JAXBContext context) {
		this.context = context;
	}

	private Resource resource;

	public Resource getResource() {
		return resource;
	}

	@Required
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public Class<?> getObjectType() {
		return Object.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	protected Object createInstance() throws Exception {

		final Resource resource = getResource();

		final InputSource inputSource = new InputSource(resource
				.getInputStream());
		try {
			inputSource.setSystemId(resource.getURL().toString());
		} catch (IOException ignored) {

		}
		final Object unmarshallingResult = getContext().createUnmarshaller()
				.unmarshal(inputSource);
		if (unmarshallingResult instanceof JAXBElement<?>) {
			return ((JAXBElement<?>) unmarshallingResult).getValue();
		} else {
			return unmarshallingResult;
		}
	}
}
