package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

import jakarta.xml.bind.JAXBElement;

public class JAXBToStringStrategy extends DefaultToStringStrategy {

	private String jaxbElementStart = "<";

	private String jaxbElementEnd = ">";

	protected void appendJAXBElementStart(StringBuilder stringBuilder) {
		stringBuilder.append(jaxbElementStart);
	}

	protected void appendJAXBElementEnd(StringBuilder stringBuilder) {
		stringBuilder.append(jaxbElementEnd);
	}

	@Override
	protected StringBuilder appendInternal(ObjectLocator locator,
			StringBuilder stringBuilder, Object value) {
		if (value instanceof JAXBElement) {
			@SuppressWarnings("rawtypes")
			final JAXBElement jaxbElement = (JAXBElement) value;
			appendInternal(locator, stringBuilder, jaxbElement);
		} else {
			super.appendInternal(locator, stringBuilder, value);
		}
		return stringBuilder;
	}

	protected StringBuilder appendInternal(ObjectLocator locator,
			StringBuilder stringBuilder, @SuppressWarnings("rawtypes") JAXBElement value) {
		appendJAXBElementStart(stringBuilder);
		stringBuilder.append(value.getName());
		appendContentStart(stringBuilder);
		append(locator, stringBuilder, value.getValue());
		appendContentEnd(stringBuilder);
		appendJAXBElementEnd(stringBuilder);
		return stringBuilder;
	}

	public static final JAXBToStringStrategy INSTANCE = new JAXBToStringStrategy();

	public static JAXBToStringStrategy getInstance() {
		return INSTANCE;
	}
}
