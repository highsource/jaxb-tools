package org.jvnet.jaxb.annox.model;

import org.apache.commons.lang3.Validate;

/**
 * Defines an annotated method or constructor parameter.
 *
 * @author Aleksei Valikov
 */
public class XParameter extends XAnnotated {

	/**
	 * Type of the parameter.
	 */
	private final Class<?> type;

	/**
	 * Constructs an annotated method parameter.
	 *
	 * @param type
	 *            parameter type.
	 * @param xannotations
	 *            parameter annotations.
	 */
	public XParameter(Class<?> type, XAnnotation<?>[] xannotations) {
		super(xannotations);
		Validate.notNull(type, "Parameter type must not be null.");
		this.type = type;
	}

	/**
	 * Returns type of the method parameter.
	 *
	 * @return Type of the method parameter.
	 */
	public Class<?> getType() {
		return type;
	}
}
