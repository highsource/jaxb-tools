package org.jvnet.jaxb2_commons.lang;

import jakarta.xml.bind.JAXBElement;

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
	
	public static final JAXBToStringStrategy INSTANCE2 = new JAXBToStringStrategy();
	@SuppressWarnings("deprecation")
	public static final ToStringStrategy INSTANCE = INSTANCE2;

	public static JAXBToStringStrategy getInstance() {
		return INSTANCE2;
	}
}