package org.hisrc.xml.xsom;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
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
import com.sun.xml.xsom.visitor.XSVisitor;

public class FindXSElementDeclVisitor implements XSVisitor {

	private final QName name;

	private XSElementDecl elementDecl = null;

	public FindXSElementDeclVisitor(final QName name) {
		Validate.notNull(name);
		this.name = name;
	}

	public XSElementDecl getElementDecl() {
		return elementDecl;
	}

	public void annotation(XSAnnotation ann) {
	}

	public void attGroupDecl(XSAttGroupDecl decl) {
	}

	public void attributeDecl(XSAttributeDecl decl) {
	}

	public void attributeUse(XSAttributeUse use) {
	}

	public void complexType(XSComplexType type) {
	}

	public void facet(XSFacet facet) {
	}

	public void identityConstraint(XSIdentityConstraint decl) {
	}

	public void notation(XSNotation notation) {
	}

	public void schema(XSSchema schema) {
	}

	public void xpath(XSXPath xp) {
	}

	public void elementDecl(XSElementDecl decl) {
		final QName declName = StringUtils.isEmpty(decl.getTargetNamespace()) ? new QName(
				decl.getName()) : new QName(decl.getTargetNamespace(),
				decl.getName());
		if (this.name.equals(declName)) {
			this.elementDecl = decl;
		}
	}

	public void modelGroup(XSModelGroup group) {
		for (XSParticle child : group.getChildren()) {
			child.visit(this);
		}
	}

	public void modelGroupDecl(XSModelGroupDecl decl) {
		decl.getModelGroup().visit(this);
	}

	public void wildcard(XSWildcard wc) {
	}

	public void empty(XSContentType empty) {
	}

	public void particle(XSParticle particle) {
		particle.getTerm().visit(this);
	}

	public void simpleType(XSSimpleType simpleType) {
	}
}
