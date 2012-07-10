package org.jvnet.jaxb2_commons.locator;

import java.text.MessageFormat;

/**
 * Validation event locator.
 * 
 * @author Aleksei Valikov
 */
public final class DefaultPropertyObjectLocator extends AbstractObjectLocator
		implements PropertyObjectLocator {

	/**
	 * Field name.
	 */
	protected final String propertyName;

	/**
	 * Constructs a new validation event locator.
	 * 
	 * @param parentLocator
	 *            parent location (may be <code>null</code>).
	 * @param object
	 *            object.
	 * @param propertyName
	 *            field name.
	 */
	protected DefaultPropertyObjectLocator(final ObjectLocator parentLocator,
			final String propertyName, final Object propertyValue) {
		super(parentLocator, propertyValue);
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Returns parameters for message formatting.
	 * 
	 * @return Message formatting parameters.
	 */
	public Object[] getMessageParameters() {
		return new Object[] { getObject(), getPropertyName() };
	}

	@Override
	protected String getDefaultMessage() {
		return MessageFormat.format("Field: {1}\nField value: {0}.",
				getMessageParameters());
	}

	@Override
	protected String getStepAsString() {
		return "." + getPropertyName();
	}

}
