package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstant;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModel;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackage;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xmlschema.XmlSchemaConstants;

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
import com.sun.xml.bind.v2.model.core.TypeInfoSet;
import com.sun.xml.bind.v2.model.core.TypeRef;
import com.sun.xml.bind.v2.model.core.ValuePropertyInfo;
import com.sun.xml.bind.v2.model.core.WildcardTypeInfo;

public abstract class CMInfoFactory<T, C, F, M> {

	private final Map<QName, MBuiltinLeafInfo> builtins = new HashMap<QName, MBuiltinLeafInfo>();

	{
		for (QName name : XmlSchemaConstants.TYPE_NAMES) {
			builtins.put(name, new CMBuiltinLeafInfo(name));
		}
	}

	private final Map<ClassInfo<T, C>, MClassInfo> classInfos = new IdentityHashMap<ClassInfo<T, C>, MClassInfo>();

	private final TypeInfoSet<T, C, F, M> typeInfoSet;

	public CMInfoFactory(TypeInfoSet<T, C, F, M> typeInfoSet) {
		Validate.notNull(typeInfoSet);
		this.typeInfoSet = typeInfoSet;

	}

	public MModel createModel() {
		Collection<? extends BuiltinLeafInfo<T, C>> builtins = typeInfoSet
				.builtins().values();
		Collection<MBuiltinLeafInfo> builtinLeafInfos = new ArrayList<MBuiltinLeafInfo>(
				builtins.size());
		for (BuiltinLeafInfo<T, C> builtinLeafInfo : builtins) {
			builtinLeafInfos.add(getTypeInfo(builtinLeafInfo));

		}
		Collection<? extends ClassInfo<T, C>> beans = typeInfoSet.beans()
				.values();
		Collection<MClassInfo> classInfos = new ArrayList<MClassInfo>(
				beans.size());
		for (ClassInfo<T, C> classInfo : beans) {
			classInfos.add(getTypeInfo(classInfo));
		}
		Collection<? extends EnumLeafInfo<T, C>> enums = typeInfoSet.enums()
				.values();
		Collection<MEnumLeafInfo> enumLeafInfos = new ArrayList<MEnumLeafInfo>(
				enums.size());
		for (EnumLeafInfo<T, C> enumLeafInfo : enums) {
			enumLeafInfos.add(getTypeInfo(enumLeafInfo));
		}

		Iterable<? extends ElementInfo<T, C>> elements = typeInfoSet
				.getAllElements();
		final Collection<MElementInfo> elementInfos = new ArrayList<MElementInfo>();
		for (ElementInfo<T, C> element : elements) {

			QName elementName = element.getElementName();
			MClassInfo scope = element.getScope() == null ? null
					: getTypeInfo(element.getScope());
			MTypeInfo typeInfo = getTypeInfo(element);
			QName substitutionHead = element.getSubstitutionHead() == null ? null
					: element.getSubstitutionHead().getElementName();
			final MElementInfo elementInfo = new CMElementInfo(
					getPackage(element), elementName, scope, typeInfo,
					substitutionHead);
			elementInfos.add(elementInfo);
		}
		return new CMModel(builtinLeafInfos, classInfos, enumLeafInfos,
				elementInfos);

	}

	protected MTypeInfo getTypeInfo(PropertyInfo<T, C> propertyInfo,
			TypeInfo<T, C> typeInfo, boolean list, Adapter<T, C> adapter,
			ID id, MimeType mimeType) {
		final MTypeInfo ti = getTypeInfo(typeInfo);
		if (!list) {
			return ti;
		} else {
			return new CMList(ti);
		}
	}

	private MTypeInfo getTypeInfo(TypeInfo<T, C> typeInfo) {
		if (typeInfo instanceof BuiltinLeafInfo) {
			return getTypeInfo((BuiltinLeafInfo<T, C>) typeInfo);
		} else if (typeInfo instanceof EnumLeafInfo) {
			return getTypeInfo((EnumLeafInfo<T, C>) typeInfo);
		} else if (typeInfo instanceof ElementInfo) {
			return getTypeInfo((ElementInfo<T, C>) typeInfo);
			// } else if (typeInfo instanceof CClassRef) {
			// throw new UnsupportedOperationException();
		} else if (typeInfo instanceof WildcardTypeInfo) {
			return new CMWildcardTypeInfo();
		} else if (typeInfo instanceof ClassInfo) {
			return getTypeInfo((ClassInfo<T, C>) typeInfo);
		} else if (typeInfo instanceof ArrayInfo) {
			throw new UnsupportedOperationException();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private MBuiltinLeafInfo getTypeInfo(BuiltinLeafInfo<T, C> typeInfo) {
		QName typeName = typeInfo.getTypeName();
		MBuiltinLeafInfo knownBuiltin = builtins.get(typeName);
		if (knownBuiltin != null) {
			return knownBuiltin;
		} else {
			// return new CMBuiltinLeafInfo(typeName);
			throw new UnsupportedOperationException(MessageFormat.format(
					"Unsupported builtin type [{0}].", typeName));
		}
	}

	private MTypeInfo getTypeInfo(ElementInfo<T, C> info) {
		ElementPropertyInfo<T, C> p = info.getProperty();
		return getTypeInfo(p, info.getContentType(), p.isValueList(),
				p.getAdapter(), p.id(), p.getExpectedMimeType());
	}

	private MEnumLeafInfo getTypeInfo(final EnumLeafInfo<T, C> info) {

		final MTypeInfo baseTypeInfo = getTypeInfo(info.getBaseType());

		final List<MEnumConstant> constants = new ArrayList<MEnumConstant>();
		@SuppressWarnings("rawtypes")
		Iterable<? extends EnumConstant> _constants = info.getConstants();
		@SuppressWarnings("unchecked")
		final Iterable<? extends EnumConstant<T, C>> enumConstants = (Iterable<? extends EnumConstant<T, C>>) _constants;
		for (EnumConstant<?, ?> enumConstant : enumConstants) {
			constants.add(new CMEnumConstant(enumConstant.getLexicalValue()));
		}

		final QName elementName = info.getElementName();
		return new CMEnumLeafInfo(getPackage(info), getLocalName(info),
				baseTypeInfo, constants, elementName);
	}

	private MClassInfo getTypeInfo(ClassInfo<T, C> info) {

		MClassInfo mClassInfo = classInfos.get(info);

		if (mClassInfo == null) {

			final MClassInfo baseClassInfo = info.getBaseClass() == null ? null
					: getTypeInfo(info.getBaseClass());
			final QName elementName = info.isElement() ? info.getElementName()
					: null;
			CMClassInfo cmClassInfo = new CMClassInfo(getPackage(info),
					getLocalName(info), baseClassInfo,

					elementName);
			mClassInfo = cmClassInfo;
			classInfos.put(info, mClassInfo);

			final List<MPropertyInfo> properties = new ArrayList<MPropertyInfo>();

			if (info.hasAttributeWildcard()) {
				properties
						.add(new CMAnyAttributePropertyInfo("otherAttributes"));

			}
			List<? extends PropertyInfo<T, C>> ps = info.getProperties();
			for (PropertyInfo<T, C> p : ps) {
				properties.add(createPropertyInfo(p));
			}
			for (MPropertyInfo property : properties) {
				cmClassInfo.addProperty(property);
			}
		}
		return mClassInfo;
	}

	private MPropertyInfo createPropertyInfo(PropertyInfo<T, C> p) {

		if (p instanceof AttributePropertyInfo) {
			final AttributePropertyInfo<T, C> ap = (AttributePropertyInfo<T, C>) p;
			return new CMAttributePropertyInfo(ap.getName(), getTypeInfo(ap),
					ap.getXmlName());
		} else if (p instanceof ValuePropertyInfo) {
			final ValuePropertyInfo<T, C> vp = (ValuePropertyInfo<T, C>) p;
			// NonElement target = vp.getTarget();
			return new CMValuePropertyInfo(vp.getName(), getTypeInfo(vp));
		} else if (p instanceof ElementPropertyInfo) {
			// System.out.println("Element property: " + p.getName());
			final ElementPropertyInfo<T, C> ep = (ElementPropertyInfo<T, C>) p;

			List<? extends TypeRef<T, C>> types = ep.getTypes();

			if (types.size() == 1) {
				final TypeRef<T, C> typeRef = types.get(0);
				return new CMElementPropertyInfo(ep.getName(),
						ep.isCollection() && !ep.isValueList(), getTypeInfo(ep,
								typeRef), typeRef.getTagName(), ep.getXmlName());
			} else {
				final Collection<MElementTypeInfo> typedElements = new ArrayList<MElementTypeInfo>();
				for (TypeRef<T, C> typeRef : types) {
					typedElements.add(new CMElementTypeInfo(typeRef
							.getTagName(), getTypeInfo(ep, typeRef)));
				}
				return new CMElementsPropertyInfo(ep.getName(),
						ep.isCollection() && !ep.isValueList(), typedElements,
						ep.getXmlName());

			}
		} else if (p instanceof ReferencePropertyInfo) {
			// System.out.println("Reference property: " + p.getName());
			final ReferencePropertyInfo<T, C> rp = (ReferencePropertyInfo<T, C>) p;
			final Set<? extends Element<T, C>> elements = rp.getElements();
			if (elements.size() == 0
					&& rp.getWildcard() != null
					&& (rp.getWildcard().allowDom || rp.getWildcard().allowTypedObject)) {
				return new CMAnyElementPropertyInfo(rp.getName(),
						rp.isCollection(), rp.isMixed(),
						rp.getWildcard().allowDom,
						rp.getWildcard().allowTypedObject);

			} else if (elements.size() == 1) {
				final Element<T, C> element = elements.iterator().next();
				return new CMElementRefPropertyInfo(rp.getName(),
						rp.isCollection(), getTypeInfo(rp, element),
						element.getElementName(), rp.getXmlName(),

						rp.isMixed(), rp.getWildcard() == null ? false
								: rp.getWildcard().allowDom,
						rp.getWildcard() == null ? false
								: rp.getWildcard().allowTypedObject);
			} else {
				final List<MElementTypeInfo> typedElements = new ArrayList<MElementTypeInfo>();
				for (Element<T, C> element : elements) {
					typedElements.add(new CMElementTypeInfo(element
							.getElementName(), getTypeInfo(rp, element)));
				}
				return new CMElementRefsPropertyInfo(rp.getName(),
						rp.isCollection(), typedElements, rp.getXmlName(),
						rp.isMixed(), rp.getWildcard() == null ? false
								: rp.getWildcard().allowDom,
						rp.getWildcard() == null ? false
								: rp.getWildcard().allowTypedObject);
			}
		} else if (p instanceof MapPropertyInfo) {
			// System.out.println("Map property: " + p.getName());
			// MapPropertyInfo<T, C> mp = (MapPropertyInfo<T, C>) p;
			throw new UnsupportedOperationException();
		} else {
			throw new AssertionError();
		}

	}

	protected MTypeInfo getTypeInfo(final ValuePropertyInfo<T, C> vp) {
		return getTypeInfo(vp, vp.ref().iterator().next(), vp.isCollection(),
				vp.getAdapter(), vp.id(), vp.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final AttributePropertyInfo<T, C> ap) {
		return getTypeInfo(ap, ap.ref().iterator().next(), ap.isCollection(),
				ap.getAdapter(), ap.id(), ap.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final ElementPropertyInfo<T, C> ep,
			final TypeRef<T, C> typeRef) {
		return getTypeInfo(ep, typeRef.getTarget(),

		ep.isValueList(), ep.getAdapter(), ep.id(), ep.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final ReferencePropertyInfo<T, C> rp,
			Element<T, C> element) {
		return getTypeInfo(rp, element, false, rp.getAdapter(), rp.id(),
				rp.getExpectedMimeType());
	}

	protected abstract MPackage getPackage(ClassInfo<T, C> info);

	protected abstract String getLocalName(ClassInfo<T, C> info);

	protected abstract MPackage getPackage(EnumLeafInfo<T, C> info);

	protected abstract String getLocalName(EnumLeafInfo<T, C> info);

	protected abstract MPackage getPackage(ElementInfo<T, C> info);

}
