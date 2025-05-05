package org.jvnet.hyperjaxb3.ejb.jpa2.plugin;

import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;

public class JPA2Plugin extends EjbPlugin {

    private IApplicationContext createApplicationContext() {
        return new ApplicationContext();
    }

	public String getOptionName() {
		return "Xhyperjaxb3-jpa2";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-jpa2: Hyperjaxb3 JPA2 plugin";
	}

}
