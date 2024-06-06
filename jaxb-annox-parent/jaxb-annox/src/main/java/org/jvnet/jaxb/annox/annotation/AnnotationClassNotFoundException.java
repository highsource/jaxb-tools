package org.jvnet.jaxb.annox.annotation;

import java.util.Objects;

public class AnnotationClassNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String className;

	public AnnotationClassNotFoundException(String className, Throwable cause) {
		super("Annotation class [" + className + "] could not be found.", cause);
        Objects.requireNonNull(className, "className should not be null");
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

}
