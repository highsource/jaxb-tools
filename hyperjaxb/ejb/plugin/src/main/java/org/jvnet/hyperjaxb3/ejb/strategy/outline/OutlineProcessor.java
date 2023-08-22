package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import java.util.Collection;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public interface OutlineProcessor<C> {
	public Collection<ClassOutline> process(C context, Outline outline,
			Options options) throws Exception;

}
