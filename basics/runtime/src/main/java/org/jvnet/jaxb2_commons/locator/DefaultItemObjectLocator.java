package org.jvnet.jaxb2_commons.locator;

import java.text.MessageFormat;

/**
 * Locator for the collection item.
 */
public final class DefaultItemObjectLocator extends AbstractObjectLocator
		implements ItemObjectLocator {
	/**
	 * Item index.
	 */
	protected final int index;

	/**
	 * Constructs a new item locator.
	 * 
	 * @param parentLocator
	 *            parent locator.
	 * @param itemIndex
	 *            item index.
	 * @param itemValue
	 *            item value.
	 */
	protected DefaultItemObjectLocator(final ObjectLocator parentLocator,
			final int itemIndex, Object itemValue) {
		super(parentLocator, itemValue);
		this.index = itemIndex;
	}

	/**
	 * Returns item index.
	 * 
	 * @return Index of the item.
	 */
	public int getIndex() {
		return index;
	}

	public Object[] getMessageParameters() {
		return new Object[] { getObject(), Integer.valueOf(getIndex()) };
	}

	@Override
	protected String getDefaultMessage() {
		return MessageFormat.format("Item index: {1}\nItem value: {0}.",
				getMessageParameters());
	}

	@Override
	protected String getStepAsString() {
		return "[" + getIndex() + "]";
	}
}
