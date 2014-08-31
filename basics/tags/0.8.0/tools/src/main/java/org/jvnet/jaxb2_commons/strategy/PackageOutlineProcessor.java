package org.jvnet.jaxb2_commons.strategy;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.PackageOutline;

public interface PackageOutlineProcessor<T, C> {
	public T process(C context, PackageOutline packageOutline, Options options)
			throws Exception;

}
