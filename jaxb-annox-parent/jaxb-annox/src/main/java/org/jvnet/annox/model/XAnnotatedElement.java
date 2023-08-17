package org.jvnet.annox.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.apache.commons.lang3.Validate;

/**
 * Defines an annotated element.
 * 
 * @param <T>
 *            type of the annotated element.
 * @author Aleksei Valikov
 */
public class XAnnotatedElement<T extends AnnotatedElement> extends XAnnotated
		implements AnnotatedElement {

	/**
	 * Target annotated element.
	 */
	private final T annotatedElement;

	/**
	 * Constructs an annotated element.
	 * 
	 * @param annotatedElement
	 *            target annotated element.
	 * @param xannotations
	 *            element annotations.
	 */
	public XAnnotatedElement(T annotatedElement, XAnnotation<?>[] xannotations) {
		super(xannotations);
		Validate.notNull(annotatedElement,
				"Annotated element must not be null.");
		this.annotatedElement = annotatedElement;
	}

	/**
	 * Returns the target annotated element.
	 * 
	 * @return Target annotated element.
	 */
	public T getAnnotatedElement() {
		return annotatedElement;
	}

	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		for (final XAnnotation<?> xannotation : getXAnnotations()) {
			if (annotationClass.equals(xannotation.getAnnotationClass())) {
				return true;
			}
		}
		return false;
	}

	public <X extends Annotation> X getAnnotation(Class<X> annotationClass) {
		for (final XAnnotation<?> xannotation : getXAnnotations()) {
			if (annotationClass.equals(xannotation.getAnnotationClass())) {
				@SuppressWarnings("unchecked")
				final X result = (X) xannotation.getResult();
				return result;
			}
		}
		return null;
	}

	public Annotation[] getDeclaredAnnotations() {
		throw new UnsupportedOperationException();
	}

}