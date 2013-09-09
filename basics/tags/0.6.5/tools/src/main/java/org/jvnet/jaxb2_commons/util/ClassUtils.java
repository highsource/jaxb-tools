package org.jvnet.jaxb2_commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class ClassUtils {

	// public static String getClassName(final ClassOutline classOutline) {
	// return CodeModelUtils.getClassName(classOutline. .getTypeAsDefined());
	// }
	//

	public static boolean contains(JDefinedClass theClass, String innerClassName) {

		for (final Iterator<JDefinedClass> classes = theClass.classes(); classes
				.hasNext();) {
			final JDefinedClass innerClass = classes.next();
			if (innerClassName.equals(innerClass.name()))
				return true;
		}
		return false;
	}

	public static void _implements(JDefinedClass theClass, JClass theInterface) {
		if (!isImplementing(theClass, theInterface))
			theClass._implements(theInterface);
	}

	public static boolean isImplementing(JDefinedClass theClass,
			JClass theInterface) {
		for (Iterator<JClass> iterator = theClass._implements(); iterator
				.hasNext();) {
			final JClass implementedInterface = iterator.next();
			if (theInterface.equals(implementedInterface)) {
				return true;
			}
		}
		return false;
	}

	public static List<ClassOutline> getAncestors(ClassOutline classOutline) {
		final List<ClassOutline> classOutlines = new LinkedList<ClassOutline>();
		addAncestors(classOutline, classOutlines);
		return classOutlines;
	}

	public static List<ClassOutline> getAncestorsAndSelf(
			ClassOutline classOutline) {
		final List<ClassOutline> classOutlines = new LinkedList<ClassOutline>();
		classOutlines.add(classOutline);
		addAncestors(classOutline, classOutlines);
		return classOutlines;
	}

	private static void addAncestors(ClassOutline classOutline,
			List<ClassOutline> classOutlines) {
		if (classOutline.getSuperClass() != null) {
			final ClassOutline superClassOutline = classOutline.getSuperClass();
			addAncestors(superClassOutline, classOutlines);
		}
	}

	public static FieldOutline[] getFields(ClassOutline classOutline) {
		final List<FieldOutline> fields = new ArrayList<FieldOutline>();
		fields.addAll(Arrays.asList(classOutline.getDeclaredFields()));
		if (classOutline.getSuperClass() != null) {
			fields.addAll(Arrays
					.asList(getFields(classOutline.getSuperClass())));
		}
		return fields.toArray(new FieldOutline[fields.size()]);
	}

	public static String getPackagedClassName(final CClassInfo classInfo) {

		if (classInfo.parent() instanceof CClassInfo) {
			return getPackagedClassName((CClassInfo) classInfo.parent()) + '$'
					+ classInfo.shortName;
		} else {
			final String r = classInfo.parent().fullName();
			if (r.length() == 0)
				return classInfo.shortName;
			else
				return r + '.' + classInfo.shortName;
		}
	}
}
