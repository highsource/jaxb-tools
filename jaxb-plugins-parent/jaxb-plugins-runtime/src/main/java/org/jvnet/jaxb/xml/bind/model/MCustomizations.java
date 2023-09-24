package org.jvnet.jaxb.xml.bind.model;

import java.util.List;

public interface MCustomizations {

	public List<MCustomization> getCustomizations();

	public void addCustomization(MCustomization customization);
}
