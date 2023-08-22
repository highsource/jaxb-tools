package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.HashSet;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;

public class DefaultProcessClassInfo implements ProcessClassInfo {

	public Collection<CClassInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		final Collection<CPropertyInfo> newProperties = context
				.getProcessPropertyInfos().process(context, classInfo);

		final Collection<CClassInfo> classes = new HashSet<CClassInfo>(1);

		classes.add(classInfo);

		for (CPropertyInfo newProperty : newProperties) {
			if (newProperty.parent() == null) {
				throw new IllegalStateException("Property ["
						+ newProperty.getName(true)
						+ "] does not have a parent.");
			}
			classes.add((CClassInfo) newProperty.parent());
		}
		
		classes.addAll(context.getCreateIdClass().process(context, classInfo));

		return classes;
	}
}
