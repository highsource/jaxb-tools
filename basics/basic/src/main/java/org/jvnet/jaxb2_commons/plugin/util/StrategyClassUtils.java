package org.jvnet.jaxb2_commons.plugin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.util.JClassUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.ClassOutline;

public class StrategyClassUtils {
	public static <T> JExpression createStrategyInstanceExpression(
			JCodeModel codeModel, final Class<? extends T> strategyInterface,
			final String strategyClassName) {

		try {
			final Class<?> strategyClass = Class.forName(strategyClassName);
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
				final Field instance2Field = strategyClass.getField("INSTANCE2");
				if (instance2Field != null
						&& strategyInterface.isAssignableFrom(instance2Field
								.getType())
						&& Modifier.isStatic(instance2Field.getModifiers())
						&& Modifier.isPublic(instance2Field.getModifiers())) {
					return strategyJClass.staticRef("INSTANCE2");
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
		} catch (ClassNotFoundException cnfex) {
			final JClass strategyJClass = codeModel.ref(strategyClassName);
			return JExpr._new(strategyJClass);
		}
	}

	public static <T> Boolean superClassImplements(ClassOutline classOutline,
			Ignoring ignoring, Class<? extends T> theInterface) {
		if (classOutline.implClass != null
				&& classOutline.implClass._extends() != null) {
			if (JClassUtils.isInstanceOf(classOutline.implClass._extends(),
					theInterface)) {
				return Boolean.TRUE;
			}
		}

		if (classOutline.target.getBaseClass() != null) {
			if (!ignoring.isIgnored(classOutline.parent().getClazz(
					classOutline.target.getBaseClass()))) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}

		if (classOutline.target.getRefBaseClass() != null) {
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
		}
		// Unknown
		return null;
	}

	public static <T> Boolean superClassNotIgnored(ClassOutline classOutline,
			Ignoring ignoring) {
		if (classOutline.target.getBaseClass() != null) {
			if (!ignoring.isIgnored(classOutline.parent().getClazz(
					classOutline.target.getBaseClass()))) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (classOutline.target.getRefBaseClass() != null) {
			return Boolean.TRUE;
		} else {
			return null;
		}
	}
}
