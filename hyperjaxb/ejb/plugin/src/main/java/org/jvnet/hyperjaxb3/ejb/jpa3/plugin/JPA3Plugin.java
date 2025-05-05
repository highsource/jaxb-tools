package org.jvnet.hyperjaxb3.ejb.jpa3.plugin;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;

public class JPA3Plugin extends EjbPlugin {

    public String applicationContextClassName = ApplicationContext.class.getName();

    public String getApplicationContextClassName() {
        return applicationContextClassName;
    }

    public void setApplicationContextClassName(String applicationContextClassName) {
        this.applicationContextClassName = applicationContextClassName;
    }

    public String getOptionName() {
		return "Xhyperjaxb3-jpa3";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-jpa3: Hyperjaxb3 JPA3 plugin";
	}

}
