package org.jvnet.jaxb.annox.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DirectAnnotatedElementFactory implements AnnotatedElementFactory {

	public static DirectAnnotatedElementFactory INSTANCE = new DirectAnnotatedElementFactory();

	public static DirectAnnotatedElementFactory getInstance() {
		return INSTANCE;
	}

	public AnnotatedElement getAnnotatedElement(
			AnnotatedElement annotatedElement) throws AnnotatedElementException {
		return annotatedElement;
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(final Method method)
			throws AnnotatedElementException {
		return new MethodAnnotatedElement(method);
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(
			@SuppressWarnings("rawtypes") final Constructor constructor) throws AnnotatedElementException {
		return new ConstructorAnnotatedElement(constructor);
	}

}
