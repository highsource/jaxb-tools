package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomization;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;

public class CMCustomizations implements MCustomizations {

	private final List<MCustomization> customizations = new LinkedList<MCustomization>();
	private final List<MCustomization> unmodifiableCustomizations = Collections
			.unmodifiableList(this.customizations);

	public List<MCustomization> getCustomizations() {
		return unmodifiableCustomizations;
	}

	public void addCustomization(MCustomization customization) {
		Validate.notNull(customization);
		this.customizations.add(customization);
	}

}
