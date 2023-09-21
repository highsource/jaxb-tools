package org.jvnet.jaxb.strategy.impl;

import java.util.HashMap;
import java.util.Map;

import org.jvnet.jaxb.strategy.ClassOutlineProcessor;
import org.jvnet.jaxb.strategy.OutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class DefaultOutlineProcessor<T, C> implements
		OutlineProcessor<Map<ClassOutline, T>, C> {

	private final ClassOutlineProcessor<T, C> classOutlineProcessor;

	public DefaultOutlineProcessor(
			final ClassOutlineProcessor<T, C> classOutlineProcessor) {
		this.classOutlineProcessor = classOutlineProcessor;
	}

	public ClassOutlineProcessor<T, C> getClassOutlineProcessor() {
		return classOutlineProcessor;
	}

	public Map<ClassOutline, T> process(C context, Outline outline,
			Options options) throws Exception {

		final Map<ClassOutline, T> classMappingsMap = new HashMap<ClassOutline, T>();
		for (final ClassOutline classOutline : outline.getClasses()) {
			final T result = getClassOutlineProcessor().process(context,
					classOutline, options);
			if (result != null) {
				classMappingsMap.put(classOutline, result);
			}
		}
		return classMappingsMap;
	}

}
