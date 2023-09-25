package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.jaxb.strategy.FieldOutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public interface FieldOutlineMapping<T> extends
		FieldOutlineProcessor<T, Mapping> {

	public T process(Mapping context, FieldOutline fieldOutline, Options options);

}
