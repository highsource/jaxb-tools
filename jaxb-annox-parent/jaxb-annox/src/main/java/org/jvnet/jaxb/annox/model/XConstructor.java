package org.jvnet.jaxb.annox.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

/**
 * Defines an annotated constructor.
 *
 * @author Aleksei Valikov
 *
 */
public class XConstructor extends XMember<Constructor<?>> implements
		ParameterizedAnnotatedElement {

	/**
	 * Empty array of constructors.
	 */
	public static final XConstructor[] EMPTY_ARRAY = new XConstructor[0];

	/**
	 * Annotated constructor parameters.
	 */
	private final XParameter[] parameters;

	/**
	 * Constructs an annotated constructor.
	 *
	 * @param constructor
	 *            target constructor.
	 * @param xannotations
	 *            constructor annotations.
	 * @param parameters
	 *            annotated constructor parameters.
	 */
	public XConstructor(Constructor<?> constructor,
			XAnnotation<?>[] xannotations, XParameter[] parameters) {
		super(constructor, xannotations);
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
		final Constructor<?> constructor = getConstructor();
		final XParameter[] parameters = getParameters();
		final Class<?>[] parameterTypes = constructor.getParameterTypes();
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
	 * Returns the target constructor.
	 *
	 * @return Target constructor.
	 */
	public Constructor<?> getConstructor() {
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
