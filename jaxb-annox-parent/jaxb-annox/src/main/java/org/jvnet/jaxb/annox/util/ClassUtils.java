package org.jvnet.jaxb.annox.util;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClassUtils {

    /**
     * The package separator character: {@code '&#x2e;' == {@value}}.
     */
    public static final char PACKAGE_SEPARATOR_CHAR = '.';

    /**
     * The inner class separator character: {@code '$' == {@value}}.
     */
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

    /**
     * Maps a primitive class name to its corresponding abbreviation used in array class names.
     */
    private static final Map<String, String> abbreviationMap;

    /** Feed abbreviation maps. */
    static {
        final Map<String, String> map = new HashMap<>();
        map.put("int", "I");
        map.put("boolean", "Z");
        map.put("float", "F");
        map.put("long", "J");
        map.put("short", "S");
        map.put("byte", "B");
        map.put("double", "D");
        map.put("char", "C");
        abbreviationMap = Collections.unmodifiableMap(map);
    }

	private static Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
	static {
		primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
		primitiveWrapperMap.put(Byte.TYPE, Byte.class);
		primitiveWrapperMap.put(Character.TYPE, Character.class);
		primitiveWrapperMap.put(Short.TYPE, Short.class);
		primitiveWrapperMap.put(Integer.TYPE, Integer.class);
		primitiveWrapperMap.put(Long.TYPE, Long.class);
		primitiveWrapperMap.put(Double.TYPE, Double.class);
		primitiveWrapperMap.put(Float.TYPE, Float.class);
	}

	private static Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		wrapperPrimitiveMap.put(Boolean.class, Boolean.TYPE);
		wrapperPrimitiveMap.put(Byte.class, Byte.TYPE);
		wrapperPrimitiveMap.put(Character.class, Character.TYPE);
		wrapperPrimitiveMap.put(Short.class, Short.TYPE);
		wrapperPrimitiveMap.put(Integer.class, Integer.TYPE);
		wrapperPrimitiveMap.put(Long.class, Long.TYPE);
		wrapperPrimitiveMap.put(Double.class, Double.TYPE);
		wrapperPrimitiveMap.put(Float.class, Float.TYPE);
	}

	private static Map<String, Class<?>> namePrimitiveMap = new HashMap<String, Class<?>>(
			8);
	static {
		namePrimitiveMap.put("boolean", Boolean.TYPE);
		namePrimitiveMap.put("byte", Byte.TYPE);
		namePrimitiveMap.put("char", Character.TYPE);
		namePrimitiveMap.put("short", Short.TYPE);
		namePrimitiveMap.put("int", Integer.TYPE);
		namePrimitiveMap.put("long", Long.TYPE);
		namePrimitiveMap.put("double", Double.TYPE);
		namePrimitiveMap.put("float", Float.TYPE);
	}

	@SuppressWarnings("rawtypes")
	public static Class primitiveToWrapper(Class cls) {
		Validate.notNull(cls);
		if (cls.isPrimitive()) {
			return primitiveWrapperMap.get(cls);
		} else {
			return cls;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class wrapperToPrimitive(Class cls) {
		Validate.notNull(cls);
		final Class<?> primitiveClass = wrapperPrimitiveMap.get(cls);
		if (primitiveClass != null) {
			return primitiveClass;
		} else {
			return cls;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class wrapperArrayToPrimitiveArray(Class cls) {
		Validate.notNull(cls);
		Validate.isTrue(cls.isArray());

		final Class<?> componentType = cls.getComponentType();
		final Class<?> primitiveComponentType = wrapperToPrimitive(componentType);

		if (primitiveComponentType == componentType) {
			return cls;
		} else {
			Object array = Array.newInstance(primitiveComponentType, 0);
			return array.getClass();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class nameToPrimitive(String name) {
		if (name == null) {
			return null;
		} else {
			return namePrimitiveMap.get(name);
		}
	}

	public static final String ARRAY_SUFFIX = "[]";

	public static Class<?> forName(String className)
			throws ClassNotFoundException {
		return forName(className, true, Thread.currentThread()
				.getContextClassLoader());
	}

	public static Class<?> forName(String className, boolean initialize,
			ClassLoader loader) throws ClassNotFoundException {
		if (className == null) {
			return null;
		}
		if (className.endsWith(ARRAY_SUFFIX)) {
			final String componentClassName = className.substring(0,
					className.length() - ARRAY_SUFFIX.length());
			return getArrayClass(forName(componentClassName, initialize, loader));
		}

		@SuppressWarnings("rawtypes")
		final Class primitiveClass = nameToPrimitive(className);

		if (primitiveClass != null) {
			return primitiveClass;
		} else {
			return Class.forName(className, initialize, loader);
		}
	}

	public static Class<?> getArrayClass(final Class<?> componentClass) {
		final Object componentArray = Array.newInstance(componentClass, 0);
		return componentArray.getClass();
	}

	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

	public static Class<?>[] forNames(String names)
			throws ClassNotFoundException {
		return forNames(names, true, null);
	}

	public static Class<?>[] forNames(String names, boolean initialize,
			ClassLoader loader) throws ClassNotFoundException {
		if (names == null) {
			return null;
		}
		final String n = names.trim();

		if ("".equals(n)) {
			return EMPTY_CLASS_ARRAY;
		}
		final String[] classNames = n.split(",");
		final Class<?>[] classes = new Class<?>[classNames.length];
		for (int index = 0; index < classNames.length; index++) {
			classes[index] = forName(classNames[index].trim(), initialize,
					loader);
		}
		return classes;
	}

    public static Class<?> getClass(String className) throws ClassNotFoundException {
        final ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
        final ClassLoader loader = contextCL == null ? ClassUtils.class.getClassLoader() : contextCL;
        return getClass(loader, className);
    }

    public static Class<?> getClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        try {
            Class<?> clazz = namePrimitiveMap.get(className);
            return clazz != null ? clazz : Class.forName(toCanonicalName(className), true, classLoader);
        } catch (final ClassNotFoundException ex) {
            // allow path separators (.) as inner class name separators
            final int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);

            if (lastDotIndex != -1) {
                try {
                    return getClass(classLoader, className.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR_CHAR + className.substring(lastDotIndex + 1));
                } catch (final ClassNotFoundException ignored) {
                    // ignore exception
                }
            }

            throw ex;
        }
    }

    /**
     * Converts a class name to a JLS style class name.
     *
     * @param className the class name
     * @return the converted name
     * @throws NullPointerException if the className is null
     */
    private static String toCanonicalName(final String className) {
        String canonicalName = StringUtils.deleteWhitespace(className);
        Objects.requireNonNull(canonicalName, "className");
        if (canonicalName.endsWith("[]")) {
            final StringBuilder classNameBuffer = new StringBuilder();
            while (canonicalName.endsWith("[]")) {
                canonicalName = canonicalName.substring(0, canonicalName.length() - 2);
                classNameBuffer.append("[");
            }
            final String abbreviation = abbreviationMap.get(canonicalName);
            if (abbreviation != null) {
                classNameBuffer.append(abbreviation);
            } else {
                classNameBuffer.append("L").append(canonicalName).append(";");
            }
            canonicalName = classNameBuffer.toString();
        }
        return canonicalName;
    }
}
