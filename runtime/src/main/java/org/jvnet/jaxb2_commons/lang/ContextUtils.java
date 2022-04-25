package org.jvnet.jaxb2_commons.lang;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ContextUtils {

	public static String getContextPath(Class<?>... classes) {
        if (classes == null) {
            throw new IllegalArgumentException("The validated object is null");
        }
        for (int i = 0; i < classes.length; i++) {
            if (classes[i] == null) {
                throw new IllegalArgumentException("The validated array contains null element at index: " + i);
            }
        }

		final StringBuilder contextPath = new StringBuilder();

		for (int index = 0; index < classes.length; index++) {
			if (index > 0) {
				contextPath.append(':');
			}
			contextPath.append(classes[index].getPackage().getName());
		}
		return contextPath.toString();
	}

	public static String toString(JAXBContext context, Object object)
			throws JAXBException {
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		final StringWriter sw = new StringWriter();
		marshaller.marshal(object, sw);
		return sw.toString();
	}
}
