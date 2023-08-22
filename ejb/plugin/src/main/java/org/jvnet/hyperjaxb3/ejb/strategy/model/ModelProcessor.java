package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.Model;

public interface ModelProcessor<C> {
	public Collection<CClassInfo> process(C context, Model model,
			Options options) throws Exception;

}
