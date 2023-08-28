package org.jvnet.jaxb.annox.model;

import java.lang.reflect.Field;

/**
 * Defines an annotated field.
 *
 * @author Aleksei Valikov
 *
 */
public class XField extends XMember<Field> {

	/**
	 * Emprty array of fields.
	 */
	public static final XField[] EMPTY_ARRAY = new XField[0];

	/**
	 * Constructs an annotated field.
	 *
	 * @param field
	 *            target field.
	 * @param xannotations
	 *            field annotations.
	 */
	public XField(Field field, XAnnotation<?>[] xannotations) {
		super(field, xannotations);
	}

	/**
	 * Returns the target field.
	 *
	 * @return Target field.
	 */
	public Field getField() {
		return getMember();
	}
}
