package org.jvnet.jaxb2.maven2.util;



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

}
