package org.jvnet.hyperjaxb3.xjc.model;

import javax.activation.MimeType;
import org.jvnet.hyperjaxb3.xml.XMLConstants;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.ejb.Constants;
import org.xml.sax.Locator;

import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.model.CAdapter;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.model.nav.NavigatorImpl;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.bind.v2.model.annotation.Locatable;
import com.sun.xml.bind.v2.model.core.ID;
import com.sun.xml.bind.v2.runtime.Location;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XmlString;

public class CExternalLeafInfo implements CNonElement, Location {

	private final NType type;
	private final QName typeName;
	private final CAdapter adapter;

	public CExternalLeafInfo(Class<?> c, String typeName,
			Class<? extends XmlAdapter<?, ?>> adapterClass) {
		this(c, new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, typeName),
				new CAdapter(adapterClass, false));
	}

	public CExternalLeafInfo(Class<?> c, QName typeName, CAdapter adapter) {
		this.type = NavigatorImpl.create(c);
		this.typeName = typeName;
		this.adapter = adapter;
	}

	public JType toType(Outline o, Aspect aspect) {
		return type.toType(o, aspect);
	}

	public boolean isCollection() {
		return false;
	}

	public ID idUse() {
		return ID.NONE;
	}

	public MimeType getExpectedMimeType() {
		return null;
	}

	public final CAdapter getAdapterUse() {
		return adapter;
	}

	public final CExternalLeafInfo getInfo() {
		return this;
	}

	public Locator getLocator() {
		return Constants.EMPTY_LOCATOR;
	}

	public final XSComponent getSchemaComponent() {
		throw new UnsupportedOperationException(
				"TODO. If you hit this, let us know.");
	}

	public QName getTypeName() {
		return typeName;
	}

	public boolean isSimpleType() {
		return true;
	}

	public boolean canBeReferencedByIDREF() {
		return false;
	}

	public NType getType() {
		return type;
	}

	public Location getLocation() {
		return this;
	}

	public Locatable getUpstream() {
		return null;
	}

	public JExpression createConstant(Outline arg0, XmlString arg1) {
		return null;
	}

	public CCustomizations getCustomizations() {
		return CCustomizations.EMPTY;
	}
}
