package org.jvnet.annox.reflect;

import java.lang.reflect.AnnotatedElement;

public class AnnotatedElementException extends Exception {

	private static final long serialVersionUID = 1L;

	private AnnotatedElement annotatedElement;

	public AnnotatedElementException(AnnotatedElement annotatedElement,
			Throwable cause) {
		super(cause);
		this.annotatedElement = annotatedElement;
	}

	public AnnotatedElement getAnnotatedElement() {
		return annotatedElement;
	}

}
