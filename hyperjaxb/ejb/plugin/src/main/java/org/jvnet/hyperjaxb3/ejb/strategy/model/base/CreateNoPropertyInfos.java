package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;

import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CPropertyInfo;

/**
 * Creates no properties.
 */
public class CreateNoPropertyInfos implements CreatePropertyInfos {

	/**
	 * A singleton instance.
	 */
	public static CreatePropertyInfos INSTANCE = new CreateNoPropertyInfos();

	public CreateNoPropertyInfos() {
	}

	/**
	 * Creates no properties, simply returns an empty collection.
	 * 
	 * @param context
	 *            ignored.
	 * @param propertyInfo
	 *            ignored.
	 * @return An empty collection.
	 */
	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return Collections.emptyList();
	}

}
