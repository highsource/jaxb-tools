package org.jvnet.jaxb.annox.reader.resourced;

import java.io.IOException;
import java.io.InputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import org.jvnet.jaxb.annox.io.NestedIOException;
import org.jvnet.jaxb.annox.util.Validate;

public class NParser {

	private final JAXBContext context;

	public NParser(JAXBContext context) {
		Validate.notNull(context);
		this.context = context;
	}

	public NParser() {
		try {
			this.context = JAXBContext.newInstance(NPackage.class);
		} catch (JAXBException jaxbex) {
			throw new ExceptionInInitializerError(jaxbex);
		}
	}

	public JAXBContext getContext() {
		return context;
	}

	public NPackage parseNPackage(InputStream is) throws IOException {
		Validate.notNull(is);
		try {
			final Object result = getContext().createUnmarshaller().unmarshal(
					is);
			return (NPackage) result;
		} catch (JAXBException jaxbex) {
			throw new NestedIOException(jaxbex);
		}

	}

	public NClass parseNClass(InputStream is) throws IOException {
		Validate.notNull(is);
		try {
			final Object result = getContext().createUnmarshaller().unmarshal(
					is);
			return (NClass) result;
		} catch (JAXBException jaxbex) {
			throw new NestedIOException(jaxbex);
		}
	}

}
