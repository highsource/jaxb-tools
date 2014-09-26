package org.jvnet.jaxb2_commons.util;

import java.util.Iterator;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;
import com.sun.xml.bind.api.impl.NameConverter;

public class OutlineUtils {

	private OutlineUtils() {
	}

	public static String getClassName(ClassOutline classOutline) {
		return CodeModelUtils.getPackagedClassName(classOutline.implClass);
	}

	public static String getFieldName(FieldOutline fieldOutline) {
		return getClassName(fieldOutline.parent()) + "."
				+ fieldOutline.getPropertyInfo().getName(true);
	}

	public static String getPropertyName(FieldOutline fieldOutline) {
		return NameConverter.standard.toVariableName(fieldOutline
				.getPropertyInfo().getName(true));
	}

	public static String getContextPath(Outline context) {
		final StringBuilder sb = new StringBuilder();

		for (final Iterator<? extends PackageOutline> packageOutlines = context
				.getAllPackageContexts().iterator(); packageOutlines.hasNext();) {
			final PackageOutline packageOutline = packageOutlines.next();
			final String packageName = packageOutline._package().name();
			sb.append(packageName);
			if (packageOutlines.hasNext())
				sb.append(':');
		}
		return sb.toString();
	}

}
