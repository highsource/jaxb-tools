package org.jvnet.hyperjaxb3.ejb.jpa3.plugin;

import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.plugin.ApplicationContext;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;

public class JPA3Plugin extends EjbPlugin {

    private IApplicationContext createApplicationContext() {
        return new ApplicationContext();
    }

    public String getOptionName() {
		return "Xhyperjaxb3-jpa3";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-jpa3: Hyperjaxb3 JPA3 plugin";
	}

}
