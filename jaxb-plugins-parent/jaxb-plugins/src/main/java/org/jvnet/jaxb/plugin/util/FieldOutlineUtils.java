package org.jvnet.jaxb.plugin.util;

import org.jvnet.jaxb.plugin.Ignoring;

import com.sun.tools.xjc.outline.FieldOutline;

public class FieldOutlineUtils {

	private FieldOutlineUtils() {

	}

	public static FieldOutline[] filter(final FieldOutline[] fieldOutlines,
			final Ignoring ignoring) {
		return ArrayUtils.filter(fieldOutlines, new Predicate<FieldOutline>() {
			public boolean evaluate(FieldOutline fieldOutline) {
				return !ignoring.isIgnored(fieldOutline);

			}
		}, FieldOutline.class);
	}
}
