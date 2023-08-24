/**
 * 
 */
package org.jvnet.jaxb.annox.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public final class ConstructorAnnotatedElement implements
		ParameterizedAnnotatedElement {
	private final Constructor<?> constructor;

	public ConstructorAnnotatedElement(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		final T annotation = constructor.<T> getAnnotation(annotationClass);
		return annotation;
	}

	public Annotation[] getAnnotations() {
		return constructor.getAnnotations();
	}

	public Annotation[] getDeclaredAnnotations() {
		return constructor.getDeclaredAnnotations();
	}

	public Annotation[][] getParameterAnnotations() {
		return constructor.getParameterAnnotations();
	}

	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		return constructor.isAnnotationPresent(annotationClass);
	}
}