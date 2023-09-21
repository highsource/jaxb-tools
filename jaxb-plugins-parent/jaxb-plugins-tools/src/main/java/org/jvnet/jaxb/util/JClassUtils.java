package org.jvnet.jaxb.util;

import java.util.Iterator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JClass;

public class JClassUtils {

	public static <T> boolean isInstanceOf(JClass _class,
			Class<? extends T> _interface) {
		Validate.notNull(_class);
		Validate.notNull(_interface);

		final String className = _class.fullName();

		try {
			if (_interface.isAssignableFrom(Class.forName(className))) {
				return true;
			}
		} catch (ClassNotFoundException cnfex) {
			// Unknown
		}

		final JClass superClass = _class._extends();
		if (superClass != null) {
			if (isInstanceOf(superClass, _interface)) {
				return true;
			}
		}

		for (final Iterator<? extends JClass> implementsIterator = _class
				._implements(); implementsIterator.hasNext();) {
			final JClass superInterface = implementsIterator.next();

			if (isInstanceOf(superInterface, _interface)) {
				return true;
			}
		}

		return false;
	}

}
