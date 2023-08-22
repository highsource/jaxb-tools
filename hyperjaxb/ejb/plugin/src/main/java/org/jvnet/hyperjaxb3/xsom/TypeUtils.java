package org.jvnet.hyperjaxb3.xsom;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;

import com.sun.xml.xsom.XSComponent;

public class TypeUtils {

	public static List<QName> getTypeNames(XSComponent component) {
		Validate.notNull(component);
		final SimpleTypeVisitor visitor = new SimpleTypeVisitor();
		component.visit(visitor);
		return visitor.getTypeNames();
	}

}
