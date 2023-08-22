package org.jvnet.hyperjaxb2.customizations.test;
import java.util.List;

import org.jvnet.jaxbcommons.addon.tests.AbstractAddOnTest;

public class RunAddOn extends AbstractAddOnTest {
	
	public List getAddonOptions() {
		final List options = super.getAddonOptions();
		options.add("-Xequals");
		options.add("-XhashCode");
		options.add("-Xhyperjaxb2");
		return options;
	}

}
