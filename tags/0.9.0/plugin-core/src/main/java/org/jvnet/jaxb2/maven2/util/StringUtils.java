package org.jvnet.jaxb2.maven2.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	/**
	 * Checks if a (trimmed) String is <code>null</code> or empty.
	 * 
	 * @param string
	 *            the String to check
	 * @return <code>true</code> if the string is <code>null</code>, or length
	 *         zero once trimmed.
	 */
	public static boolean isEmpty(String string) {
		return (string == null || string.trim().length() == 0);
	}

	public static String escapeSpace(String url) {
		// URLEncoder didn't work.
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < url.length(); i++) {
			// TODO: not sure if this is the only character that needs to be
			// escaped.
			if (url.charAt(i) == ' ')
				buf.append("%20");
			else
				buf.append(url.charAt(i));
		}
		return buf.toString();
	}

	private static String[] EMPTY_STRING_ARRAY = new String[0];

	public static String[] split(String str, char separatorChar,
			boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<String>();
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
}
