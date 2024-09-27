package org.jvnet.jaxb.plugin.util;

import org.jvnet.jaxb.plugin.Ignoring;

import com.sun.tools.xjc.outline.FieldOutline;

import java.util.List;

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

    public static List<FieldOutline> filterToList(final FieldOutline[] fieldOutlines,
                                                  final Ignoring ignoring) {
        return ArrayUtils.filterToList(fieldOutlines, new Predicate<FieldOutline>() {
            public boolean evaluate(FieldOutline fieldOutline) {
                return !ignoring.isIgnored(fieldOutline);

            }
        }, FieldOutline.class);
    }
}
