package org.jvnet.jaxb.annox.model.annotation.field;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.model.XAnnotationFieldVisitor;

/**
 * Defines an annotation field.
 * 
 * @param <T>
 *            Type of the field.
 * @author Aleksei Valikov
 */
public abstract class XAnnotationField<T> {

	/**
	 * Name of the field.
	 */
	private final String name;

	/**
	 * Returns the name of the field.
	 * 
	 * @return Name of the field.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of the field.
	 * 
	 * @return Value of the field.
	 */
	public abstract T getValue();

	/**
	 * Returns the internal value of the field used for {@link #hashCode()},
	 * {@link #equals(Object)} and {@link #toString()} operations. Defaults to
	 * the value of the field.
	 * 
	 * @return Returns the value of the field.
	 */
	protected final Object getInternalValue() {
		return getValue();
	}

	/**
	 * Returns the result value of the field. By default, returns the value of
	 * the field.
	 * 
	 * @return Result value of the field.
	 */
	public Object getResult() {
		return getValue();
	}

	/**
	 * Return the type of the field.
	 * 
	 * @return Type of the field.
	 */
	public abstract Class<?> getType();

	/**
	 * Accepts the annotation visitor.
	 * 
	 * @param
	 * <P>
	 * Return type of the visitor.
	 * @param visitor
	 *            visitor to accept.
	 * @return Result of the visit.
	 */
	public abstract <P> P accept(XAnnotationFieldVisitor<P> visitor);

	/**
	 * Constructrs an annotation field with the given name.
	 * 
	 * @param name
	 *            name of the field.
	 */
	public XAnnotationField(final String name) {
		Validate.notNull(name, "Field name must not be null.");
		this.name = name;
	}
}
