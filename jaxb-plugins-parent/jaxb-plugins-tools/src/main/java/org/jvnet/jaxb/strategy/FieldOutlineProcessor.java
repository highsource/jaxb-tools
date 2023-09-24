package org.jvnet.jaxb.strategy;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public interface FieldOutlineProcessor<T, C> {
	public T process(C context, FieldOutline fieldOutline, Options options) throws Exception;

}
