package org.jvnet.jaxb2_commons.plugin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.jvnet.jaxb2_commons.plugin.Ignoring;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.ClassOutline;

public class StrategyClassUtils {
	public static <T> JExpression createStrategyInstanceExpression(
			JCodeModel codeModel, final Class<? extends T> strategyInterface,
			final Class<? extends T> strategyClass) {
		final JClass strategyJClass = codeModel.ref(strategyClass);
		try {
			final Method getInstanceMethod = strategyClass.getMethod(
					"getInstance", new Class<?>[0]);
			if (getInstanceMethod != null
					&& strategyInterface.isAssignableFrom(getInstanceMethod
							.getReturnType())
					&& Modifier.isStatic(getInstanceMethod.getModifiers())
					&& Modifier.isPublic(getInstanceMethod.getModifiers())) {
				return strategyJClass.staticInvoke("getInstance");
			}

		} catch (Exception ignored) {
			// Nothing to do
		}
		try {
			final Field instanceField = strategyClass.getField("INSTANCE");
			if (instanceField != null
					&& strategyInterface.isAssignableFrom(instanceField
							.getType())
					&& Modifier.isStatic(instanceField.getModifiers())
					&& Modifier.isPublic(instanceField.getModifiers())) {
				return strategyJClass.staticRef("INSTANCE");
			}
		} catch (Exception ignored) {
			// Nothing to do
		}
		return JExpr._new(strategyJClass);
	}

	public static <T> Boolean superClassImplements(ClassOutline classOutline,
			Ignoring ignoring, Class<? extends T> theInterface) {
		if (classOutline.target.getBaseClass() != null) {
			if (!ignoring.isIgnored(classOutline.parent().getClazz(
					classOutline.target.getBaseClass()))) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (classOutline.target.getRefBaseClass() != null) {
			try {
				if (theInterface.isAssignableFrom(Class
						.forName(classOutline.target.getRefBaseClass()
								.fullName()))) {
					return Boolean.TRUE;
				} else {
					return Boolean.FALSE;
				}
			} catch (ClassNotFoundException ignored) {
				// We'll assume it does implement
				return Boolean.TRUE;
			}
		} else {
			return null;
		}
	}
}
