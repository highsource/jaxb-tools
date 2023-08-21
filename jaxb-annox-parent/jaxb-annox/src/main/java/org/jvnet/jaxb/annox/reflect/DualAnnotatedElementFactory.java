package org.jvnet.jaxb.annox.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DualAnnotatedElementFactory implements AnnotatedElementFactory {

	private AnnotatedElementFactory primary;

	private AnnotatedElementFactory secondary;

	public DualAnnotatedElementFactory(AnnotatedElementFactory primary,
			AnnotatedElementFactory secondary) {
		super();
		this.primary = primary;
		this.secondary = secondary;
	}

	public DualAnnotatedElementFactory() {
		this(new ResourcedAnnotatedElementFactory(),
				new DirectAnnotatedElementFactory());
	}

	public AnnotatedElement getAnnotatedElement(
			AnnotatedElement annotatedElement) throws AnnotatedElementException {
		final AnnotatedElement primaryAnnotatedElement = primary
				.getAnnotatedElement(annotatedElement);
		if (primaryAnnotatedElement != null) {
			return primaryAnnotatedElement;
		} else {
			return secondary.getAnnotatedElement(annotatedElement);
		}
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(
			@SuppressWarnings("rawtypes") Constructor annotatedElement) throws AnnotatedElementException {
		final ParameterizedAnnotatedElement primaryAnnotatedElement = primary
				.getAnnotatedElement(annotatedElement);
		if (primaryAnnotatedElement != null) {
			return primaryAnnotatedElement;
		} else {
			return secondary.getAnnotatedElement(annotatedElement);
		}
	}

	public ParameterizedAnnotatedElement getAnnotatedElement(
			Method annotatedElement) throws AnnotatedElementException {
		final ParameterizedAnnotatedElement primaryAnnotatedElement = primary
				.getAnnotatedElement(annotatedElement);
		if (primaryAnnotatedElement != null) {
			return primaryAnnotatedElement;
		} else {
			return secondary.getAnnotatedElement(annotatedElement);
		}
	}
}
