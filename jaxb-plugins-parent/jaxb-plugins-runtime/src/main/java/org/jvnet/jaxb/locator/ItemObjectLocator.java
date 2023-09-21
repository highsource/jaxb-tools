package org.jvnet.jaxb.locator;

/**
 * Models item locator.
 *
 * @author Aleksei Valikov
 *
 */
public interface ItemObjectLocator extends ObjectLocator {

	/**
	 * @return Item index.
	 */
	public int getIndex();

	/**
	 * @return Item value.
	 */
	public Object getObject();

}
