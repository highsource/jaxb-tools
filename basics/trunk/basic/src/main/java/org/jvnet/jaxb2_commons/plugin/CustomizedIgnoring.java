package org.jvnet.jaxb2_commons.plugin;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.outline.ClassOutline;
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

	public boolean isIgnored(FieldOutline fieldOutline) {
		for (QName name : getIgnoredCustomizationElementNames()) {
			if (CustomizationUtils.containsCustomization(fieldOutline, name)) {
				return true;
			}
		}
		return false;
	}

}
