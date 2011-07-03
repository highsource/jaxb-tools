package org.jvnet.jaxb2_commons.plugin.wildcard;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.xml.bind.v2.model.core.WildcardMode;

public class WildcardPlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "Xwildcard";
	}

	@Override
	public String getUsage() {
		return "Allows specifying the wildcard mode in wildcard properties.";
	}

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {
		for (CClassInfo classInfo : model.beans().values()) {
			for (CPropertyInfo propertyInfo : classInfo.getProperties()) {
				if (propertyInfo instanceof CReferencePropertyInfo) {
					final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;

					if (CustomizationUtils.containsCustomization(
							classInfo, Customizations.LAX_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.LAX);

					} else if (CustomizationUtils.containsCustomization(
							classInfo, Customizations.SKIP_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.SKIP);

					} else if (CustomizationUtils.containsCustomization(
							classInfo, Customizations.STRICT_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.STRICT);
					}

					
					if (CustomizationUtils.containsCustomization(
							referencePropertyInfo,
							Customizations.LAX_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.LAX);

					} else if (CustomizationUtils.containsCustomization(
							referencePropertyInfo,
							Customizations.SKIP_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.SKIP);

					} else if (CustomizationUtils.containsCustomization(
							referencePropertyInfo,
							Customizations.STRICT_ELEMENT_NAME)) {
						referencePropertyInfo.setWildcard(WildcardMode.STRICT);
					}
				}
			}
		}
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(
						org.jvnet.jaxb2_commons.plugin.wildcard.Customizations.LAX_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.wildcard.Customizations.SKIP_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.wildcard.Customizations.STRICT_ELEMENT_NAME);
	}

}
