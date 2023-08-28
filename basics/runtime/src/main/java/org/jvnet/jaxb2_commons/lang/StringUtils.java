package org.jvnet.jaxb2_commons.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringUtils {
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	public static final String EMPTY = "";
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String[] split(String str, char separatorChar) {
		return splitWorker(str, separatorChar, false);
	}

	@SuppressWarnings("unchecked")
	private static String[] splitWorker(String str, char separatorChar,
			boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String join(@SuppressWarnings("rawtypes") Iterator iterator, String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return first == null ? "" : first.toString();
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16,
		// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

}
