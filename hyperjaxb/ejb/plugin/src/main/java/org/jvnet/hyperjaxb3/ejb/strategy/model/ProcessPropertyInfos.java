package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface ProcessPropertyInfos extends
		ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel>,
		PropertyInfoProcessor<Collection<CPropertyInfo>, ProcessModel> {

}
