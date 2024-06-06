package org.jvnet.jaxb.annox.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Some utils methods to validate input
 */
public class Validate {

    public static <T> T notNull(T object) {
        return notNull(object, "The validated object is null");
    }

    public static <T> T notNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "The validated expression is false");
    }

    public static void isTrue(boolean expression, String message) {
        if (expression == false) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(@SuppressWarnings("rawtypes") Collection collection) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(
                    "The validated collection is empty");
        }
    }

    public static void noNullElements(Collection<?> collection) {
        noNullElements(collection, null);
    }
    public static void noNullElements(Collection<?> collection, String message) {
        Validate.notNull(collection);
        int i = 0;
        for (Iterator<?> it = collection.iterator(); it.hasNext(); i++) {
            if (it.next() == null) {
                throw new IllegalArgumentException((message == null ? "" : message) +
                        "The validated collection contains null element at index: "
                                + i);
            }
        }
    }

    public static <T> T[] noNullElements(T[] array) {
        return noNullElements(array, null);
    }

    public static <T> T[] noNullElements(T[] array, String message) {
        Validate.notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException((message == null ? "" : message) +
                    "The validated array contains null element at index: "
                    + i);
            }
        }
        return array;
    }

}
