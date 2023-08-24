package org.jvnet.jaxb.annox.annotation;

import java.lang.annotation.Annotation;

public class NoSuchAnnotationFieldException extends Exception {

	private static final long serialVersionUID = 1L;

	private final Class<? extends Annotation> annotationType;

	private final String name;

	public NoSuchAnnotationFieldException(
			final Class<? extends Annotation> annotationType,
			final String name, Throwable cause) {
		super("No such annotation field [" + name + "] in annotation class ["
				+ (annotationType == null ? "null" : annotationType.getName())
				+ "].", cause);
		this.annotationType = annotationType;
		this.name = name;
	}

	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}

	public String getName() {
		return name;
	}
}
