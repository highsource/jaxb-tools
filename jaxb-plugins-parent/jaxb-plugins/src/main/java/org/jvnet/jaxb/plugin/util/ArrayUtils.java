package org.jvnet.jaxb.plugin.util;

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

	/**
	 * <p>Outputs an array as a String, treating {@code null} as an empty array.</p>
	 *
	 * <p>Multi-dimensional arrays are handled correctly, including
	 * multi-dimensional primitive arrays.</p>
	 *
	 * <p>The format is that of Java source code, for example {@code {a,b}}.</p>
	 *
	 * @param array  the array to get a toString for, may be {@code null}
	 * @return a String representation of the array, '{}' if null array input
	 */
	public static String toString(final Object array) {
		if (array == null) {
			return "{}";
		}
		if (!array.getClass().isArray()) {
			return array.toString();
		}

		final StringBuilder builder = new StringBuilder();
		builder.append('{');

		final int length = Array.getLength(array);
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				builder.append(", ");
			}

			final Object item = Array.get(array, i);
			if (item == null) {
				builder.append("null");
			} else if (item.getClass().isArray()) {
				builder.append(toString(item));
			} else {
				builder.append(item.toString());
			}
		}

		builder.append('}');
		return builder.toString();
	}

}
