package org.jvnet.jaxb2_commons.plugin.util;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class ArrayUtils {

	private ArrayUtils() {

	}

	public static <T> T[] filter(T[] array, Predicate<T> predicate,
			Class<? extends T> theClass) {

		if (array == null) {
			return null;
		} else {
			final List<T> list = new LinkedList<T>();
			for (T item : array) {
				if (predicate.evaluate(item)) {
					list.add(item);
				}
			}
			@SuppressWarnings("unchecked")
			final T[] newArray = (T[]) Array.newInstance(theClass, list.size());
			return list.toArray(newArray);
		}

	}

}
