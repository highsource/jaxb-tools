package org.jvnet.jaxb.annox.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

/**
 * Defines an annotated method.
 * 
 * @author Aleksei Valikov
 */
public class XMethod extends XMember<Method> implements
		ParameterizedAnnotatedElement {

	/**
	 * Empty array of methods.
	 */
	public static final XMethod[] EMPTY_ARRAY = new XMethod[0];

	/**
	 * Annotated method parameters.
	 */
	private final XParameter[] parameters;

	/**
	 * Constructs an annotated method.
	 * 
	 * @param method
	 *            target method.
	 * @param xannotations
	 *            annotations of the target method.
	 * @param parameters
	 *            annotated method parameters.
	 */
	public XMethod(final Method method, XAnnotation<?>[] xannotations,
			XParameter[] parameters) {
		super(method, xannotations);
		Validate.noNullElements(parameters);
		this.parameters = parameters;
		check();
	}

	/**
	 * Returns annotated parameters of the method.
	 * 
	 * @return Annotated parameters of the method.
	 */
	public XParameter[] getParameters() {
		return parameters;
	}

	/**
	 * Checks if annoated parameters match method parameters.
	 * 
	 * @throws IllegalArgumentException
	 *             If annotated parameters do not match method parameters.
	 */
	private void check() {
		final Method method = getMethod();
		final XParameter[] parameters = getParameters();
		final Class<?>[] parameterTypes = method.getParameterTypes();
		Validate.isTrue(parameters.length == parameterTypes.length,
				"Wrong number of parameters: [" + parameters.length
						+ "], expected [" + parameterTypes.length + "].");
		for (int index = 0; index < parameters.length; index++) {
			final XParameter parameter = parameters[index];
			final Class<?> parameterType = parameterTypes[index];
			Validate.isTrue(parameterType.equals(parameter.getType()),
					"Wrong parameter type: [" + parameter.getType()
							+ "], expected [" + parameterType + "]");
		}
	}

	/**
	 * Returns the target methid.
	 * 
	 * @return Target method.
	 */
	public Method getMethod() {
		return getMember();
	}

	public Annotation[][] getParameterAnnotations() {
		final XParameter[] xparameters = getParameters();
		final Annotation[][] parameterAnnotations = new Annotation[xparameters.length][];
		for (int index = 0; index < xparameters.length; index++) {
			parameterAnnotations[index] = xparameters[index].getAnnotations();
		}
		return parameterAnnotations;
	}
}
