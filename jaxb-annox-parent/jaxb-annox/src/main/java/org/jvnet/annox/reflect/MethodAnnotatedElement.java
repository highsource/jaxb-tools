/**
 * 
 */
package org.jvnet.annox.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class MethodAnnotatedElement implements
		ParameterizedAnnotatedElement {
	private final Method method;

	public MethodAnnotatedElement(Method method) {
		this.method = method;
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return method.<T> getAnnotation(annotationClass);
	}

	public Annotation[] getAnnotations() {
		return method.getAnnotations();
	}

	public Annotation[] getDeclaredAnnotations() {
		return method.getDeclaredAnnotations();
	}

	public Annotation[][] getParameterAnnotations() {
		return method.getParameterAnnotations();
	}

	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass);
	}
}