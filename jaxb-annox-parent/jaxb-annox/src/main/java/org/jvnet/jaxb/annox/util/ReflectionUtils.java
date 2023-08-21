package org.jvnet.jaxb.annox.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.Validate;

public class ReflectionUtils {

	public static Constructor<?> getConstructor(Class<?> theClass,
			Class<?>[] parameterTypes) throws NoSuchMethodException {
		Validate.notNull(theClass);
		if (parameterTypes == null) {
			try {
				return theClass
						.getDeclaredConstructor(ClassUtils.EMPTY_CLASS_ARRAY);
			} catch (NoSuchMethodException ignored) {
			}
			Constructor<?> foundMethod = null;
			for (final Constructor<?> method : theClass
					.getDeclaredConstructors()) {
				if (foundMethod == null) {
					foundMethod = method;
				} else {
					throw new NoSuchMethodException("Duplicate constructors ["
							+ foundMethod + "] and [" + method + "].");
				}
			}
			if (foundMethod != null) {
				return foundMethod;
			} else {
				throw new NoSuchMethodException();
			}
		} else {
			return theClass.getDeclaredConstructor(parameterTypes);
		}
	}

	public static Method getMethod(Class<?> theClass, String methodName,
			Class<?>[] parameterTypes) throws NoSuchMethodException {
		Validate.notNull(theClass);
		Validate.notNull(methodName);
		if (parameterTypes == null) {
			try {
				return theClass.getMethod(methodName,
						ClassUtils.EMPTY_CLASS_ARRAY);
			} catch (NoSuchMethodException ignored) {
			}
			Method foundMethod = null;
			for (final Method method : theClass.getDeclaredMethods()) {
				if (methodName.equals(method.getName())) {
					if (foundMethod == null) {
						foundMethod = method;
					} else {
						throw new NoSuchMethodException("Duplicate methods ["
								+ foundMethod + "] and [" + method + "].");
					}
				}
			}
			if (foundMethod != null) {
				return foundMethod;
			} else {
				throw new NoSuchMethodException();
			}
		} else {
			return theClass.getDeclaredMethod(methodName, parameterTypes);
		}
	}

	public static Field getField(Class<?> theClass, String fieldName)
			throws NoSuchFieldException {
		return theClass.getDeclaredField(fieldName);
	}
}
