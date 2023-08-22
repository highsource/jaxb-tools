package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.model.CPropertyInfo;

/**
 * Creates properties for the given property.
 */
public interface CreatePropertyInfos extends
		PropertyInfoProcessor<Collection<CPropertyInfo>, ProcessModel> {

	/**
	 * Creates a collection of properties for the given property.
	 * 
	 * @param context
	 *            processing context.
	 * @param propertyInfo
	 *            property to be processed.
	 * @return Collection of properties created for the given property. Must not
	 *         be
	 *         <code><code>null</code>, if nothing is created, return an empty collection instead.
	 */
	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo);
}
