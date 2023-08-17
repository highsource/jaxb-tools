package org.jvnet.annox.annotation;

import org.apache.commons.lang3.Validate;

public class AnnotationClassNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String className;

	public AnnotationClassNotFoundException(String className, Throwable cause) {
		super("Annotation class [" + className + "] could not be found.", cause);
		Validate.notNull(className);
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

}
