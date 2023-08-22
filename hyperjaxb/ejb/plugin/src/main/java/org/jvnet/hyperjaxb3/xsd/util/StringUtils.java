package org.jvnet.hyperjaxb3.xsd.util;

public class StringUtils {

	public static String normalizeString(String text) {
		if (text == null) {
			return null;
		}

		int len = text.length();

		// most of the texts are already in the collapsed form.
		// so look for the first whitespace in the hope that we will
		// never see it.
		int s = 0;
		while (s < len) {
			if (isWhiteSpace(text.charAt(s)))
				break;
			s++;
		}
		if (s == len)
			// the input happens to be already collapsed.
			return text;

		// we now know that the input contains spaces.
		// let's sit down and do the collapsing normally.

		StringBuffer result = new StringBuffer(len /*
													 * allocate enough size to
													 * avoid re-allocation
													 */);

		if (s != 0) {
			for (int i = 0; i < s; i++)
				result.append(text.charAt(i));
			result.append(' ');
		}

		boolean inStripMode = true;
		for (int i = s + 1; i < len; i++) {
			char ch = text.charAt(i);
			boolean b = isWhiteSpace(ch);
			if (inStripMode && b)
				continue; // skip this character

			inStripMode = b;
			if (inStripMode)
				result.append(' ');
			else
				result.append(ch);
		}

		// remove trailing whitespaces
		len = result.length();
		if (len > 0 && result.charAt(len - 1) == ' ')
			result.setLength(len - 1);
		// whitespaces are already collapsed,
		// so all we have to do is to remove the last one character
		// if it's a whitespace.

		return result.toString();

	}

	public static boolean isWhiteSpace(char ch) {
		if (ch > 0x20)
			return false;

		return ch == 0x9 || ch == 0xA || ch == 0xD || ch == 0x20;
	}

}
