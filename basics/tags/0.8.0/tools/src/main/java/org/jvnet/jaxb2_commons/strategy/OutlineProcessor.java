package org.jvnet.jaxb2_commons.strategy;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.Outline;

public interface OutlineProcessor<T, C> {

	public T process(C context, Outline outline, Options options) throws Exception;

}
