package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MAnyElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MValuePropertyInfo;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationUse;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class AnnotatePropertyVisitor implements
		MPropertyInfoVisitor<NType, NClass, Void> {

	private final JAnnotatable annotatable;

	public AnnotatePropertyVisitor(final JAnnotatable annotatable) {
		Validate.notNull(annotatable);
		this.annotatable = annotatable;
	}

	public Void visitAnyAttributePropertyInfo(
			MAnyAttributePropertyInfo<NType, NClass> info) {
		this.annotatable.annotate(XmlAnyAttribute.class);
		return null;
	}

	public Void visitValuePropertyInfo(MValuePropertyInfo<NType, NClass> info) {
		this.annotatable.annotate(XmlValue.class);
		return null;
	}

	public Void visitAttributePropertyInfo(
			MAttributePropertyInfo<NType, NClass> info) {

		JAnnotationUse annotation = this.annotatable
				.annotate(XmlAttribute.class);

		final String name = info.getAttributeName().getLocalPart();
		final String namespace = info.getAttributeName().getNamespaceURI();

		annotation.param("name", name);

		// generate namespace property?
		if (!namespace.equals("")) { // assume attributeFormDefault ==
										// unqualified
			annotation.param("namespace", namespace);
		}

		// TODO
		// if(info.isRequired()) {
		// xaw.required(true);
		// }

		return null;
	}

	public Void visitElementPropertyInfo(
			MElementPropertyInfo<NType, NClass> info) {
		throw new UnsupportedOperationException();
	}

	public Void visitElementsPropertyInfo(
			MElementsPropertyInfo<NType, NClass> info) {
		throw new UnsupportedOperationException();
	}

	public Void visitAnyElementPropertyInfo(
			MAnyElementPropertyInfo<NType, NClass> info) {
		throw new UnsupportedOperationException();
	}

	public Void visitElementRefPropertyInfo(
			MElementRefPropertyInfo<NType, NClass> info) {
		throw new UnsupportedOperationException();
	}

	public Void visitElementRefsPropertyInfo(
			MElementRefsPropertyInfo<NType, NClass> info) {
		throw new UnsupportedOperationException();
	}

}
