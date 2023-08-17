package org.jvnet.annox.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class ClassUtils {

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
		final String[] classNames = StringUtils.split(n, ',');
		final Class<?>[] classes = new Class<?>[classNames.length];
		for (int index = 0; index < classNames.length; index++) {
			classes[index] = forName(classNames[index].trim(), initialize,
					loader);
		}
		return classes;
	}
}
