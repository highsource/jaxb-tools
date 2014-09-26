package org.jvnet.jaxb2_commons.lang;

import javax.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class JAXBToStringStrategy extends DefaultToStringStrategy {

	private String jaxbElementStart = "<";

	private String jaxbElementEnd = ">";

	protected void appendJAXBElementStart(StringBuilder stringBuilder) {
		stringBuilder.append(jaxbElementStart);
	}

	protected void appendJAXBElementEnd(StringBuilder stringBuilder) {
		stringBuilder.append(jaxbElementEnd);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected StringBuilder appendInternal(ObjectLocator locator,
			StringBuilder stringBuilder, Object value) {
		if (value instanceof JAXBElement) {
			final JAXBElement jaxbElement = (JAXBElement) value;
			appendInternal(locator, stringBuilder, jaxbElement);
		} else {
			super.appendInternal(locator, stringBuilder, value);
		}
		return stringBuilder;
	}

	@SuppressWarnings("unchecked")
	protected StringBuilder appendInternal(ObjectLocator locator,
			StringBuilder stringBuilder, JAXBElement value) {
		appendJAXBElementStart(stringBuilder);
		stringBuilder.append(value.getName());
		appendContentStart(stringBuilder);
		append(locator, stringBuilder, value.getValue());
		appendContentEnd(stringBuilder);
		appendJAXBElementEnd(stringBuilder);
		return stringBuilder;
	}
	
	public static final ToStringStrategy INSTANCE = new JAXBToStringStrategy();

}
