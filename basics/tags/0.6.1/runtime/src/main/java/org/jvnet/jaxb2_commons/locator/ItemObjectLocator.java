package org.jvnet.jaxb2_commons.locator;

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
