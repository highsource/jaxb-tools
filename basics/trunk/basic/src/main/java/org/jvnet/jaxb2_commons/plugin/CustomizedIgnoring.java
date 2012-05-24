package org.jvnet.jaxb2_commons.plugin;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class CustomizedIgnoring implements Ignoring {

	private final QName[] ignoredCustomizationElementNames;

	public CustomizedIgnoring(QName... names) {
		this.ignoredCustomizationElementNames = names;
	}

	public QName[] getIgnoredCustomizationElementNames() {
		return ignoredCustomizationElementNames;
	}

	public boolean isIgnored(ClassOutline classOutline) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(classOutline, name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIgnored(EnumOutline enumOutline) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(enumOutline, name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIgnored(FieldOutline fieldOutline) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(fieldOutline, name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIgnored(CClassInfo classInfo) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(classInfo, name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIgnored(CEnumLeafInfo enumLeafInfo) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(enumLeafInfo, name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIgnored(CPropertyInfo propertyInfo) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(propertyInfo, name)) {
				return true;
			}
		}
		return false;
	}

}
