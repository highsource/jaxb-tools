package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface CreateDefaultIdPropertyInfos extends
		ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> {

	public boolean isTransient();

	public void setTransient(boolean transientField);

}
