package org.jvnet.jaxb.locator;

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
	 *            locator of the parent object.
	 * @param propertyName
	 *            name of the property.
	 * @param propertyValue
	 *            value of the property.
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
