package org.hisrc.xml.xsom;

import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSIdentityConstraint;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSModelGroupDecl;
import com.sun.xml.xsom.XSNotation;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSWildcard;
import com.sun.xml.xsom.XSXPath;
import com.sun.xml.xsom.visitor.XSFunction;

public class DefaultFunctionImpl<T> implements XSFunction<T> {

	protected T defaultValue(XSComponent component) {
		return null;
	}

	protected T defaultValue(XSAnnotation annotation) {
		return null;
	}

	@Override
	public T simpleType(XSSimpleType simpleType) {
		return defaultValue(simpleType);
	}

	@Override
	public T particle(XSParticle particle) {
		return defaultValue(particle);
	}

	@Override
	public T empty(XSContentType empty) {
		return defaultValue(empty);
	}

	@Override
	public T wildcard(XSWildcard wc) {
		return defaultValue(wc);
	}

	@Override
	public T modelGroupDecl(XSModelGroupDecl decl) {
		return defaultValue(decl);
	}

	@Override
	public T modelGroup(XSModelGroup group) {
		return defaultValue(group);
	}

	@Override
	public T elementDecl(XSElementDecl decl) {
		return defaultValue(decl);
	}

	@Override
	public T annotation(XSAnnotation ann) {
		return defaultValue(ann);
	}

	@Override
	public T attGroupDecl(XSAttGroupDecl decl) {
		return defaultValue(decl);
	}

	@Override
	public T attributeDecl(XSAttributeDecl decl) {
		return defaultValue(decl);
	}

	@Override
	public T attributeUse(XSAttributeUse use) {
		return defaultValue(use);
	}

	@Override
	public T complexType(XSComplexType type) {
		return defaultValue(type);
	}

	@Override
	public T schema(XSSchema schema) {
		return defaultValue(schema);
	}

	@Override
	public T facet(XSFacet facet) {
		return defaultValue(facet);
	}

	@Override
	public T notation(XSNotation notation) {
		return defaultValue(notation);
	}

	@Override
	public T identityConstraint(XSIdentityConstraint decl) {
		return defaultValue(decl);
	}

	@Override
	public T xpath(XSXPath xpath) {
		return defaultValue(xpath);
	}
}
