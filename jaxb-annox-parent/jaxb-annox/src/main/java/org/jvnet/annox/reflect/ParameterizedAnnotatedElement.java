package org.jvnet.annox.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface ParameterizedAnnotatedElement extends AnnotatedElement {

	/**
	 * Returns an array of arrays that represent the annotations on the formal
	 * parameters, in declaration order, of the method represented by this
	 * parameterized annotated element. (Returns an array of length zero if the
	 * underlying element is parameterless. If the method has one or more
	 * parameters, a nested array of length zero is returned for each parameter
	 * with no annotations.) The caller of this method is free to modify the
	 * returned arrays; it will have no effect on the arrays returned to other
	 * callers.
	 * 
	 * @return an array of arrays that represent the annotations on the formal
	 *         parameters, in declaration order.
	 */
	public Annotation[][] getParameterAnnotations();

}
