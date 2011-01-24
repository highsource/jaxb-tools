package org.jvnet.jaxb2_commons.util;

import java.util.Iterator;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JClassContainer;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JResourceFile;
import com.sun.codemodel.JStatement;
import com.sun.codemodel.JType;
import com.sun.codemodel.fmt.JPropertyFile;
import com.sun.tools.xjc.outline.ClassOutline;

/**
 * Property file utilities.
 * 
 * @author valikov
 */
public class CodeModelUtils {
	private CodeModelUtils() {
	}

	/**
	 * Returns a property file (created if necessary).
	 * 
	 * @param thePackage
	 *            package to create property file
	 * @param name
	 *            property file name.
	 * @return Property file.
	 */

	public static JPropertyFile getOrCreatePropertyFile(JPackage thePackage,
			String name) {
		JPropertyFile propertyFile = null;
		for (@SuppressWarnings("unchecked")
		Iterator<JResourceFile> iterator = thePackage.propertyFiles(); iterator
				.hasNext()
				&& (null == propertyFile);) {
			final JResourceFile resourceFile = (JResourceFile) iterator.next();
			if (resourceFile instanceof JPropertyFile
					&& name.equals(resourceFile.name())) {
				propertyFile = (JPropertyFile) resourceFile;
			}
		}

		if (null == propertyFile) {
			propertyFile = new JPropertyFile(name);
			thePackage.addResourceFile(propertyFile);
		}
		return propertyFile;
	}

	// public static boolean doesImplement(JDefinedClass theClass, JClass
	// theInterface)
	// {
	// theClass._i
	// }

	public static String getClassName(final JClass theClass) {
		return (theClass.outer() == null ? theClass.fullName()
				: getClassName(theClass.outer()) + "$" + theClass.name());
	}

	public static String getLocalClassName(final JClass theClass) {
		return (theClass.outer() == null ? theClass.name()
				: getLocalClassName(theClass.outer()) + "$" + theClass.name());
	}

	public static String getDottedLocalClassName(final JClass theClass) {
		return (theClass.outer() == null ? theClass.name()
				: getDottedLocalClassName(theClass.outer()) + "."
						+ theClass.name());
	}

	public static String getPackagedClassName(final JClass theClass) {
		return (theClass.outer() == null ? theClass.fullName()
				: getPackagedClassName(theClass.outer()) + "$"
						+ theClass.name());
	}

	public static JClass box(JType t) {
		if (t instanceof JClass)
			return (JClass) t;
		else
			return ((JPrimitiveType) t).boxify();
	}

	public static JDefinedClass getOrCreateClass(JClassContainer container,
			int flags, String name) {
		try {
			return container._class(flags, name);
		} catch (JClassAlreadyExistsException jcaeex) {
			return jcaeex.getExistingClass();
		}
	}

	public static JDefinedClass getOrCreateClass(JCodeModel codeModel,
			int flags, String fullClassName) {
		final String packageName;
		final String className;
		final int lastDotIndex = fullClassName.lastIndexOf('.');
		if (lastDotIndex >= 0) {
			packageName = fullClassName.substring(0, lastDotIndex);
			className = fullClassName.substring(lastDotIndex + 1);
		} else {
			packageName = "";
			className = fullClassName;
		}
		final JPackage thePackage = codeModel._package(packageName);
		return getOrCreateClass(thePackage, flags, className);
	}

	public static JStatement split(JDefinedClass theClass,
			JStatement[] statements, String prefix, int start, int length,
			int threshold) {
		final JMethod method = theClass.method(JMod.PRIVATE + JMod.STATIC,
				theClass.owner().VOID, prefix);
		if (length < threshold) {
			for (int index = start; (index - start) < length; index++) {
				final JStatement statement = statements[index];
				method.body().add(statement);
			}
		} else {
			method.body().add(
					split(theClass, statements, prefix + "_0", start,
							length / 2, threshold));
			method.body().add(
					split(theClass, statements, prefix + "_1", start + length
							/ 2, length - (length / 2), threshold));
		}
		return JExpr.invoke(method);
	}

	public static JMethod getMethod(JDefinedClass theClass, String name,
			JType[] arguments) {
		final JMethod method = theClass.getMethod(name, arguments);
		if (method != null) {
			return method;
		} else {
			final JClass draftSuperClass = theClass._extends();
			if (draftSuperClass == null
					|| !(draftSuperClass instanceof JDefinedClass)) {
				return null;
			} else {
				final JDefinedClass superClass = (JDefinedClass) draftSuperClass;
				return getMethod(superClass, name, arguments);
			}
		}
	}

	public static JMethod getMethod(final JDefinedClass theClass,
			final String name) {
		for (JMethod method : theClass.methods()) {
			if (method.name().equals(name))
				return method;
		}
		return null;
	}

	public static JMethod getMethod(final ClassOutline classOutline,
			final String name) {
		final JDefinedClass ref = classOutline.ref;
		final JMethod method = getMethod(ref, name);
		if (method != null) {
			return method;
		} else {
			final ClassOutline superClassOutline = classOutline.getSuperClass();
			if (superClassOutline == null) {
				return null;
			} else {
				return getMethod(superClassOutline, name);
			}
		}
	}

	public static JCodeModel getCodeModel(ClassOutline classOutline) {
		return classOutline.ref.owner();
	}

	public static JType ref(JCodeModel codeModel, String className) {
		try {
			// try the context class loader first
			return codeModel.ref(Thread.currentThread().getContextClassLoader()
					.loadClass(className));
		} catch (ClassNotFoundException e) {
			// fall through
		}
		// then the default mechanism.
		try {
			return codeModel.ref(Class.forName(className));
		} catch (ClassNotFoundException e1) {
			// fall through
		}

		{
			JDefinedClass _class = _getClass(codeModel, className);
			if (_class != null) {
				return _class;
			}
		}
		return codeModel.ref(className.replace('$', '.'));
	}

	public static JDefinedClass _getClass(JCodeModel codeModel,
			String fullyQualifiedName) {
		final int idx = fullyQualifiedName.lastIndexOf('.');
		if (idx < 0) {
			return codeModel.rootPackage()._getClass(fullyQualifiedName);
		} else {
			final String packageName = fullyQualifiedName.substring(0, idx);
			for (Iterator<JPackage> iterator = codeModel.packages(); iterator
					.hasNext();) {
				final JPackage _package = iterator.next();
				if (packageName.equals(_package.name())) {
					return _getClass(_package, fullyQualifiedName
							.substring(idx + 1));
				}
			}
			return null;
		}

	}

	public static JDefinedClass _getClass(JPackage _package, String name) {
		final int idx = name.lastIndexOf('$');
		if (idx < 0) {
			return _package._getClass(name);
		} else {
			final String parentClassName = name.substring(0, idx);
			final JDefinedClass parentClass = _package
					._getClass(parentClassName);
			if (parentClass == null) {
				return null;
			} else {
				return _getClass(parentClass, name.substring(idx + 1));
			}

		}

	}

	public static JDefinedClass _getClass(JDefinedClass _class, String name) {
		final int idx = name.lastIndexOf('$');
		if (idx < 0) {
			for (Iterator<JDefinedClass> nestedClasses = _class.classes(); nestedClasses
					.hasNext();) {
				final JDefinedClass nestedClass = nestedClasses.next();
				if (nestedClass.name().equals(name)) {
					return nestedClass;
				}
			}
			return null;

		} else {
			final JDefinedClass nestedClass = _getClass(_class, name.substring(
					0, idx));
			if (nestedClass == null) {
				return null;
			} else {
				return _getClass(nestedClass, name.substring(idx + 1));
			}
		}
	}

}