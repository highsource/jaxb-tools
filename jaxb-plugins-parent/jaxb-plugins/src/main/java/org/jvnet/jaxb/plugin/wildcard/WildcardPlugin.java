package org.jvnet.jaxb.plugin.wildcard;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.Model;
import org.glassfish.jaxb.core.v2.model.core.WildcardMode;

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
		final boolean laxModel = CustomizationUtils.containsCustomization(
				model, Customizations.LAX_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                model, LegacyCustomizations.LAX_ELEMENT_NAME);
		final boolean skipModel = CustomizationUtils.containsCustomization(
				model, Customizations.SKIP_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                model, LegacyCustomizations.SKIP_ELEMENT_NAME);
		final boolean strictModel = CustomizationUtils.containsCustomization(
				model, Customizations.STRICT_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                model, LegacyCustomizations.STRICT_ELEMENT_NAME);
		for (CClassInfo classInfo : model.beans().values()) {
			final boolean laxClassInfo = CustomizationUtils.containsCustomization(
                classInfo, Customizations.LAX_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                classInfo, LegacyCustomizations.LAX_ELEMENT_NAME);
			final boolean skipClassInfo = CustomizationUtils.containsCustomization(
                classInfo, Customizations.SKIP_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                classInfo, LegacyCustomizations.SKIP_ELEMENT_NAME);
			final boolean strictClassInfo = CustomizationUtils.containsCustomization(
                classInfo, Customizations.STRICT_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                classInfo, LegacyCustomizations.STRICT_ELEMENT_NAME);
			for (CPropertyInfo propertyInfo : classInfo.getProperties()) {
				if (propertyInfo instanceof CReferencePropertyInfo) {
					final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;
					final boolean laxPropertyInfo = CustomizationUtils.containsCustomization(
                        referencePropertyInfo, Customizations.LAX_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                        referencePropertyInfo, LegacyCustomizations.LAX_ELEMENT_NAME);
					final boolean skipPropertyInfo = CustomizationUtils.containsCustomization(
                        referencePropertyInfo, Customizations.SKIP_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                        referencePropertyInfo, LegacyCustomizations.SKIP_ELEMENT_NAME);
					final boolean strictPropertyInfo = CustomizationUtils.containsCustomization(
                        referencePropertyInfo, Customizations.STRICT_ELEMENT_NAME) || CustomizationUtils.containsCustomization(
                        referencePropertyInfo, LegacyCustomizations.STRICT_ELEMENT_NAME);

					if (laxModel) {
						referencePropertyInfo.setWildcard(WildcardMode.LAX);
					} else if (skipModel) {
						referencePropertyInfo.setWildcard(WildcardMode.SKIP);
					} else if (strictModel) {
						referencePropertyInfo.setWildcard(WildcardMode.STRICT);
					}

					if (laxClassInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.LAX);
					} else if (skipClassInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.SKIP);
					} else if (strictClassInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.STRICT);
					}

					if (laxPropertyInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.LAX);
					} else if (skipPropertyInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.SKIP);
					} else if (strictPropertyInfo) {
						referencePropertyInfo.setWildcard(WildcardMode.STRICT);
					}
				}
			}
		}
	}

    @Override
    public Collection<QName> getCustomizationElementNames() {
        return Arrays.asList(
            org.jvnet.jaxb.plugin.wildcard.Customizations.LAX_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.wildcard.Customizations.SKIP_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.wildcard.Customizations.STRICT_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.wildcard.LegacyCustomizations.LAX_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.wildcard.LegacyCustomizations.SKIP_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.wildcard.LegacyCustomizations.STRICT_ELEMENT_NAME);
    }

}
