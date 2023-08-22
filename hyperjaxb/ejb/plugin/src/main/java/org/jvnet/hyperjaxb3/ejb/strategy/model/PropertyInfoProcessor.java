package org.jvnet.hyperjaxb3.ejb.strategy.model;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface PropertyInfoProcessor<T, C> {

	public T process(C context, CPropertyInfo propertyInfo);

}
