package org.jvnet.jaxb.annox.parser.exception;

import java.text.MessageFormat;

public class ValueParseException extends Exception {

	private static final long serialVersionUID = 1L;

	private Class<?> targetClass;

	private Object value;

	public ValueParseException(Object value, Class<?> targetClass, Throwable cause) {
		this(value, targetClass);
		this.value = value;
	}

	public ValueParseException(Object value, Class<?> targetClass) {
		super(MessageFormat.format(
				"Could not parse the value [{0}] into the target class [{1}].",
				(value == null ? "null" : value.toString()),
				(targetClass == null ? "null" : targetClass.getName())));
		this.targetClass = targetClass;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

}
