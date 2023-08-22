package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.LinkedList;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ClassInfoProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;

public class GetIdPropertyInfos implements
		ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		final Collection<CPropertyInfo> ids = new LinkedList<CPropertyInfo>();

		if (classInfo.getBaseClass() != null) {
			ids.addAll(process(context, classInfo.getBaseClass()));
		}

		if (!CustomizationUtils.containsCustomization(classInfo,
				Customizations.IGNORED_ELEMENT_NAME)) {

			for (CPropertyInfo propertyInfo : classInfo.getProperties()) {
				if ((CustomizationUtils.containsCustomization(propertyInfo,
						Customizations.ID_ELEMENT_NAME) || CustomizationUtils
						.containsCustomization(propertyInfo,
								Customizations.EMBEDDED_ID_ELEMENT_NAME))
						&& !CustomizationUtils.containsCustomization(
								propertyInfo,
								Customizations.IGNORED_ELEMENT_NAME)) {
					ids.add(propertyInfo);
				}
			}
		}
		return ids;
	}

}
