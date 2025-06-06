package org.jvnet.hyperjaxb3.xsom;

import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;

import com.sun.xml.xsom.XSComponent;

public class TypeUtils {

	public static List<QName> getTypeNames(XSComponent component) {
		Objects.requireNonNull(component, "Component must not be null.");
		final SimpleTypeVisitor visitor = new SimpleTypeVisitor();
		component.visit(visitor);
		return visitor.getTypeNames();
	}

}
