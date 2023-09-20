package org.jvnet.jaxb2_commons.lang;

import java.util.Collection;
import java.util.Iterator;

public class Validate {

	public static void notNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("The validated object is null");
		}
	}

	public static void isTrue(boolean expression) {
		if (expression == false) {
			throw new IllegalArgumentException(
					"The validated expression is false");
		}
	}

	public static void notEmpty(@SuppressWarnings("rawtypes") Collection collection) {
		if (collection == null || collection.size() == 0) {
			throw new IllegalArgumentException(
					"The validated collection is empty");
		}
	}

	public static void noNullElements(Collection<?> collection) {
		Validate.notNull(collection);
		int i = 0;
		for (Iterator<?> it = collection.iterator(); it.hasNext(); i++) {
			if (it.next() == null) {
				throw new IllegalArgumentException(
						"The validated collection contains null element at index: "
								+ i);
			}
		}
	}

}
