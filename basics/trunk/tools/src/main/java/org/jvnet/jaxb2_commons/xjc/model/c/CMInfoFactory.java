package org.jvnet.jaxb2_commons.xjc.model.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.activation.MimeType;

import org.jvnet.jaxb2_commons.xjc.model.MClassInfo;
import org.jvnet.jaxb2_commons.xjc.model.MEnumConstant;
import org.jvnet.jaxb2_commons.xjc.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xjc.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypedElement;

import com.sun.tools.xjc.model.CClassRef;
import com.sun.xml.bind.v2.model.core.Adapter;
import com.sun.xml.bind.v2.model.core.ArrayInfo;
import com.sun.xml.bind.v2.model.core.AttributePropertyInfo;
import com.sun.xml.bind.v2.model.core.BuiltinLeafInfo;
import com.sun.xml.bind.v2.model.core.ClassInfo;
import com.sun.xml.bind.v2.model.core.Element;
import com.sun.xml.bind.v2.model.core.ElementInfo;
import com.sun.xml.bind.v2.model.core.ElementPropertyInfo;
import com.sun.xml.bind.v2.model.core.EnumConstant;
import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
import com.sun.xml.bind.v2.model.core.ID;
import com.sun.xml.bind.v2.model.core.MapPropertyInfo;
import com.sun.xml.bind.v2.model.core.PropertyInfo;
import com.sun.xml.bind.v2.model.core.ReferencePropertyInfo;
import com.sun.xml.bind.v2.model.core.TypeInfo;
import com.sun.xml.bind.v2.model.core.TypeRef;
import com.sun.xml.bind.v2.model.core.ValuePropertyInfo;
import com.sun.xml.bind.v2.model.core.WildcardTypeInfo;

public class CMInfoFactory {

	public MTypeInfo getTypeInfo(TypeInfo typeInfo, boolean collection,
			Adapter adapter, ID id, MimeType mimeType) {
		throw new UnsupportedOperationException();
	}

	public MTypeInfo getTypeInfo(TypeInfo typeInfo) {
		if (typeInfo instanceof EnumLeafInfo) {
			return getTypeInfo((EnumLeafInfo) typeInfo);
		} else if (typeInfo instanceof ElementInfo) {
			return getTypeInfo((ElementInfo) typeInfo);
		} else if (typeInfo instanceof CClassRef) {
			throw new UnsupportedOperationException();
		} else if (typeInfo instanceof BuiltinLeafInfo) {
			throw new UnsupportedOperationException();
		} else if (typeInfo instanceof WildcardTypeInfo) {
			return new CMWildcardTypeInfo();
		} else if (typeInfo instanceof ClassInfo) {
			return getTypeInfo((ClassInfo) typeInfo);
		} else if (typeInfo instanceof ArrayInfo) {
			throw new UnsupportedOperationException();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public MTypeInfo getTypeInfo(ElementInfo ei) {
		ElementPropertyInfo property = ei.getProperty();
		return getTypeInfo(ei, property.isCollection(), property.getAdapter(),
				property.id(), property.getExpectedMimeType());
	}

	public MEnumLeafInfo getTypeInfo(final EnumLeafInfo eli) {
		final List<MEnumConstant> constants = new ArrayList<MEnumConstant>();

		Iterable<? extends EnumConstant> enumConstants = eli.getConstants();
		for (EnumConstant<?, ?> enumConstant : enumConstants) {
			constants.add(new CMEnumConstant(enumConstant.getLexicalValue()));
		}
		return new CMEnumLeafInfo(getTypeInfo(eli.getBaseType()), constants);
	}

	public MClassInfo getTypeInfo(ClassInfo classInfo) {
		final List<MPropertyInfo> properties = new ArrayList<MPropertyInfo>();

		if (classInfo.hasAttributeWildcard()) {
			properties.add(new CMAnyAttributePropertyInfo("otherAttributes"));

		}
		List<PropertyInfo> ps = classInfo.getProperties();
		for (PropertyInfo p : ps) {
			properties.add(createPropertyInfo(p));
		}
		return new CMClassInfo(classInfo.getName(),
				getTypeInfo(classInfo.getBaseClass()), properties);
	}

	public MPropertyInfo createPropertyInfo(PropertyInfo p) {

		if (p instanceof AttributePropertyInfo) {
			final AttributePropertyInfo ap = (AttributePropertyInfo) p;
			return new CMAttributePropertyInfo(
					ap.getName(),
					ap.isCollection(),
					getTypeInfo(ap.getTarget(), ap.isCollection(),
							ap.getAdapter(), ap.id(), ap.getExpectedMimeType()),
					ap.getXmlName());
		} else if (p instanceof ValuePropertyInfo) {
			final ValuePropertyInfo vp = (ValuePropertyInfo) p;
			return new CMValuePropertyInfo(vp.getName(), vp.isCollection(),
					getTypeInfo(vp.getTarget(), vp.isCollection(),
							vp.getAdapter(), vp.id(), vp.getExpectedMimeType()));
		} else if (p instanceof ElementPropertyInfo) {
			final ElementPropertyInfo ep = (ElementPropertyInfo) p;

			List<TypeRef> types = ep.getTypes();

			if (types.size() == 1) {
				final TypeRef typeRef = types.get(0);
				return new CMElementPropertyInfo(ep.getName(),
						ep.isCollection(), getTypeInfo(typeRef.getTarget(),
								ep.isCollection(), ep.getAdapter(), ep.id(),
								ep.getExpectedMimeType()),
						typeRef.getTagName(), ep.getXmlName(), ep.isValueList());
			} else {
				final Collection<MTypedElement> typedElements = new ArrayList<MTypedElement>();
				for (TypeRef typeRef : types) {
					typedElements.add(new CMTypedElement(typeRef.getTagName(),
							getTypeInfo(typeRef.getTarget(), ep.isCollection(),
									ep.getAdapter(), ep.id(),
									ep.getExpectedMimeType())));
				}
				return new CMElementsPropertyInfo(ep.getName(),
						ep.isCollection(), typedElements, ep.getXmlName(),
						ep.isValueList());

			}
		} else if (p instanceof ReferencePropertyInfo) {
			final ReferencePropertyInfo rp = (ReferencePropertyInfo) p;
			final Set<Element> elements = rp.getElements();
			if (elements.size() == 1) {
				final Element element = elements.iterator().next();
				return new CMElementRefPropertyInfo(rp.getName(),
						rp.isCollection(), getTypeInfo(element,
								rp.isCollection(), rp.getAdapter(), rp.id(),
								rp.getExpectedMimeType()),
						element.getElementName(), rp.getXmlName(),

						rp.isMixed(), rp.getWildcard() == null ? false
								: rp.getWildcard().allowDom,
						rp.getWildcard() == null ? false
								: rp.getWildcard().allowTypedObject);
			} else {
				final List<MTypedElement> typedElements = new ArrayList<MTypedElement>();
				for (Element element : elements) {
					typedElements.add(new CMTypedElement(element
							.getElementName(), getTypeInfo(element,
							rp.isCollection(), rp.getAdapter(), rp.id(),
							rp.getExpectedMimeType())));
				}
				return new CMElementRefsPropertyInfo(rp.getName(),
						rp.isCollection(), typedElements, rp.getXmlName(),
						rp.isMixed(), rp.getWildcard() == null ? false
								: rp.getWildcard().allowDom,
						rp.getWildcard() == null ? false
								: rp.getWildcard().allowTypedObject);
			}
		} else if (p instanceof MapPropertyInfo) {
			MapPropertyInfo mp = (MapPropertyInfo) p;
			throw new UnsupportedOperationException();
		} else {
			throw new AssertionError();
		}

	}
}
