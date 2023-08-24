package org.jvnet.jaxb.annox.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface AnnotatedElementFactory {

	public AnnotatedElement getAnnotatedElement(
			AnnotatedElement annotatedElement) throws AnnotatedElementException;

	public ParameterizedAnnotatedElement getAnnotatedElement(Method method)
			throws AnnotatedElementException;

	public ParameterizedAnnotatedElement getAnnotatedElement(
			Constructor<?> constructor) throws AnnotatedElementException;
}
