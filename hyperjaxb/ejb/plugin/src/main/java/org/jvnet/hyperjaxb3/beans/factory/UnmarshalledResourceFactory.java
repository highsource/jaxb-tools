package org.jvnet.hyperjaxb3.beans.factory;

import java.io.InputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import org.xml.sax.InputSource;

public class UnmarshalledResourceFactory {

	public static Object createInstance(InputStream stream, String systemId, JAXBContext context) throws Exception {
		final InputSource inputSource = new InputSource(stream);
        inputSource.setSystemId(systemId);
		final Object unmarshallingResult = context.createUnmarshaller().unmarshal(inputSource);
		if (unmarshallingResult instanceof JAXBElement<?>) {
			return ((JAXBElement<?>) unmarshallingResult).getValue();
		} else {
			return unmarshallingResult;
		}
	}
}
