package org.jvnet.jaxb2_commons.lang;

public class ClassUtils {

	private ClassUtils() {
	}

	public static final char PACKAGE_SEPARATOR_CHAR = '.';

	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

	/**
	 * <p>
	 * Gets the class name minus the package name from a <code>Class</code>.
	 * </p>
	 *
	 * @param cls
	 *            the class to get the short name for.
	 * @return the class name without the package name or an empty string
	 */
	public static String getShortClassName(@SuppressWarnings("rawtypes") Class cls) {
		if (cls == null) {
			return "";
		}
		return getShortClassName(cls.getName());
	}

	/**
	 * <p>
	 * Gets the class name minus the package name from a String.
	 * </p>
	 *
	 * <p>
	 * The string passed in is assumed to be a class name - it is not checked.
	 * </p>
	 *
	 * @param className
	 *            the className to get the short name for
	 * @return the class name of the class without the package name or an empty
	 *         string
	 */
	public static String getShortClassName(String className) {
		if (className == null) {
			return "";
		}
		if (className.length() == 0) {
			return "";
		}
		char[] chars = className.toCharArray();
		int lastDot = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == PACKAGE_SEPARATOR_CHAR) {
				lastDot = i + 1;
			} else if (chars[i] == INNER_CLASS_SEPARATOR_CHAR) { // handle inner
				// classes
				chars[i] = PACKAGE_SEPARATOR_CHAR;
			}
		}
		return new String(chars, lastDot, chars.length - lastDot);
	}

}
