package org.jvnet.jaxb2_commons.locator;

import jakarta.xml.bind.ValidationEventLocator;

import org.jvnet.jaxb2_commons.i18n.Reportable;

/**
 * Object locator denotes a location in an object structure.
 * 
 * @author Aleksei Valikov
 * 
 */
public interface ObjectLocator extends ValidationEventLocator, Reportable {

	/**
	 * @return Parent locator, may be <code>null</code>.
	 */
	public ObjectLocator getParentLocator();

	/**
	 * @return Path to this locator from the root.
	 */
	public ObjectLocator[] getPath();

	/**
	 * @return Path to this locator in string form;
	 */
	public String getPathAsString();

	/**
	 * Creates a locator for the property, relative to this locator.
	 * 
	 * @param propertyName
	 *            name of the property, must not be <code>null</code>.
	 * @param propertyValue
	 *            value of the property, may be <code>null</code>.
	 * @return Child property locator.
	 */
	public PropertyObjectLocator property(String propertyName,
			Object propertyValue);

	/**
	 * Creates a locator for the item (like list or array item) relative to this
	 * locator.
	 * 
	 * @param itemIndex
	 *            index of the item.
	 * @param itemValue
	 *            value of the item.
	 * @return Child item locator.
	 */
	public ItemObjectLocator item(int itemIndex, Object itemValue);

}
