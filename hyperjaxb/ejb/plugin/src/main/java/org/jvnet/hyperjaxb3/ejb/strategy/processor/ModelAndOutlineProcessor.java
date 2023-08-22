package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ModelProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;

public interface ModelAndOutlineProcessor<C> extends
		ModelProcessor<C>, OutlineProcessor<C> {

}
