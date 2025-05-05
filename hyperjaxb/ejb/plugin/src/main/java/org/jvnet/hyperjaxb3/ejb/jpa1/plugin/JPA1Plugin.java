package org.jvnet.hyperjaxb3.ejb.jpa1.plugin;

import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;

public class JPA1Plugin extends EjbPlugin {

    private IApplicationContext createApplicationContext() {
        return new ApplicationContext();
    }

	public String getOptionName() {
		return "Xhyperjaxb3-jpa1";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-jpa1: Hyperjaxb3 JPA1 plugin";
	}

}
