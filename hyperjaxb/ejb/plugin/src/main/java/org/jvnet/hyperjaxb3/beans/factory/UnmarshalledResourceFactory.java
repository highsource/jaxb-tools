package org.jvnet.hyperjaxb3.beans.factory;

import java.io.InputStream;

import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

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
