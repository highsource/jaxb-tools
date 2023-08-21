package org.jvnet.jaxb.annox.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.reader.XReader;
import org.jvnet.jaxb.annox.reader.resourced.ResourcedXReader;

public class ResourcedAnnotatedElementFactory implements
		AnnotatedElementFactory {

	private final XReader xreader;

	public ResourcedAnnotatedElementFactory(XReader xreader) {
		Validate.notNull(xreader);
		this.xreader = xreader;
	}

	public ResourcedAnnotatedElementFactory() {
		this(new ResourcedXReader());
	}

	public XReader getXReader() {
		return xreader;
	}

	public AnnotatedElement getAnnotatedElement(
			AnnotatedElement annotatedElement) throws AnnotatedElementException {
		Validate.notNull(annotatedElement);
		if (annotatedElement instanceof Package) {
			return getXReader().getXPackage((Package) annotatedElement);
		} else if (annotatedElement instanceof Class<?>) {
			return getXReader().getXClass((Class<?>) annotatedElement);
		} else if (annotatedElement instanceof Field) {
			return getXReader().getXField((Field) annotatedElement);
		}

		else if (annotatedElement instanceof Constructor) {
			return getXReader().getXConstructor(
					(Constructor<?>) annotatedElement);
		} else if (annotatedElement instanceof Method) {
			return getXReader().getXMethod((Method) annotatedElement);
		} else {
			throw new AssertionError("Unexpected annotated element ["
					+ annotatedElement + "]");
		}
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(
			@SuppressWarnings("rawtypes") Constructor constructor) throws AnnotatedElementException {
		Validate.notNull(constructor);
		return getXReader().getXConstructor(constructor);
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(Method method)
			throws AnnotatedElementException {
		Validate.notNull(method);
		return getXReader().getXMethod(method);
	}
}
