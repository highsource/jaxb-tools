package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MElement;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementTypeRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMAnyAttributePropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMBuiltinLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementTypeRefOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMWildcardTypeInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MBuiltinLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementTypeRefOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MWildcardTypeInfoOrigin;

import com.sun.xml.bind.v2.model.core.Adapter;
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

@SuppressWarnings("unchecked")
public abstract class CMInfoFactory<T, C extends T, TIS extends TypeInfoSet<T, C, ?, ?>,
//
TI extends TypeInfo<T, C>,
//
BLI extends BuiltinLeafInfo<T, C>,
//
E extends Element<T, C>,
//
EI extends ElementInfo<T, C>,
//
ELI extends EnumLeafInfo<T, C>,
//
EC extends EnumConstant<T, C>,
//
CI extends ClassInfo<T, C>,
//
PI extends PropertyInfo<T, C>,
//
API extends AttributePropertyInfo<T, C>,
//
VPI extends ValuePropertyInfo<T, C>,
//
EPI extends ElementPropertyInfo<T, C>,
//
RPI extends ReferencePropertyInfo<T, C>,
//
WTI extends WildcardTypeInfo<T, C>,
//
TR extends TypeRef<T, C>> {

	private final Map<BLI, MBuiltinLeafInfo<T, C>> builtinLeafInfos = new IdentityHashMap<BLI, MBuiltinLeafInfo<T, C>>();

	private final Map<CI, MClassInfo<T, C>> classInfos = new IdentityHashMap<CI, MClassInfo<T, C>>();

	private final Map<ELI, MEnumLeafInfo<T, C>> enumLeafInfos = new IdentityHashMap<ELI, MEnumLeafInfo<T, C>>();

	private final Map<EI, MElementInfo<T, C>> elementInfos = new IdentityHashMap<EI, MElementInfo<T, C>>();

	private final TIS typeInfoSet;

	public CMInfoFactory(TIS typeInfoSet) {
		Validate.notNull(typeInfoSet);
		this.typeInfoSet = typeInfoSet;

	}
	
	public TIS getTypeInfoSet() {
		return typeInfoSet;
	}

	public MModelInfo<T, C> createModel() {
		final CMModel<T, C> model = new CMModel<T, C>(
				createModelInfoOrigin(typeInfoSet));

		createBuiltinLeafInfos(model);
		createEnumLeafInfos(model);
		createClassInfos(model);
		createElementInfos(model);
		return model;

	}

	private void createElementInfos(final CMModel<T, C> model) {
		Iterable<? extends ElementInfo<T, C>> elements = typeInfoSet
				.getAllElements();
		for (ElementInfo<T, C> element : elements) {
			final EI ei = (EI) element;
			getElementInfo(ei);
		}
		for (ElementInfo<T, C> element : elements) {
			model.addElementInfo(getElementInfo((EI) element));
		}
	}

	private void createEnumLeafInfos(final CMModel<T, C> model) {
		Collection<? extends EnumLeafInfo<T, C>> enums = typeInfoSet.enums()
				.values();
		for (EnumLeafInfo<T, C> enumLeafInfo : enums) {
			final ELI eli = (ELI) enumLeafInfo;
			getTypeInfo(eli);
		}
		for (Map.Entry<ELI, MEnumLeafInfo<T, C>> entry : enumLeafInfos
				.entrySet()) {
			populateEnumLeafInfo(entry.getKey(), entry.getValue());
		}
		for (EnumLeafInfo<T, C> enumLeafInfo : enums) {
			model.addEnumLeafInfo(getTypeInfo((ELI) enumLeafInfo));
		}
	}

	private void createBuiltinLeafInfos(final CMModel<T, C> model) {
		Collection<? extends BuiltinLeafInfo<T, C>> builtins = typeInfoSet
				.builtins().values();
		for (BuiltinLeafInfo<T, C> builtinLeafInfo : builtins) {
			final BLI bli = (BLI) builtinLeafInfo;
			getTypeInfo(bli);
		}
		for (BuiltinLeafInfo<T, C> builtinLeafInfo : builtins) {
			model.addBuiltinLeafInfo(getTypeInfo((BLI) builtinLeafInfo));
		}
	}

	private void createClassInfos(final CMModel<T, C> model) {
		Collection<? extends ClassInfo<T, C>> beans = typeInfoSet.beans()
				.values();
		for (ClassInfo<T, C> classInfo : beans) {
			final CI ci = (CI) classInfo;
			getTypeInfo(ci);
		}
		for (Map.Entry<CI, MClassInfo<T, C>> entry : classInfos.entrySet()) {
			populateClassInfo(entry.getKey(), entry.getValue());
		}
		for (ClassInfo<T, C> classInfo : beans) {
			model.addClassInfo(getTypeInfo((CI) classInfo));
		}
	}

	protected MTypeInfo<T, C> getTypeInfo(PropertyInfo<T, C> propertyInfo,
			TI typeInfo, boolean list, Adapter<T, C> adapter, ID id,
			MimeType mimeType) {
		final MTypeInfo<T, C> ti = getTypeInfo(typeInfo);

		if (list) {
			switch (id) {
			case ID:
				final MTypeInfo<T, C> tid = new CMID<T, C>(ti.getTargetType(),
						ti);
				return new CMList<T, C>(createListType(tid.getTargetType()),
						tid, null);
			case IDREF:
				return new CMIDREFS<T, C>(createListType(ti.getTargetType()),
						ti);
			default:
				return new CMList<T, C>(createListType(ti.getTargetType()), ti,
						null);
			}
		} else {
			switch (id) {
			case ID:
				return new CMID<T, C>(ti.getTargetType(), ti);
			case IDREF:
				return new CMIDREF<T, C>(ti.getTargetType(), ti);
			default:
				return ti;
			}
		}

	}

	protected MTypeInfo<T, C> getTypeInfo(TI typeInfo) {
		if (typeInfo instanceof BuiltinLeafInfo) {
			return getTypeInfo((BLI) typeInfo);
		} else if (typeInfo instanceof EnumLeafInfo) {
			return getTypeInfo((ELI) typeInfo);
		} else if (typeInfo instanceof ElementInfo) {
			return getTypeInfo((EI) typeInfo);
		} else if (typeInfo instanceof WildcardTypeInfo) {
			return createWildcardTypeInfo((WTI) typeInfo);
		} else if (typeInfo instanceof ClassInfo) {
			return getTypeInfo((CI) typeInfo);
		} else {
			throw new UnsupportedOperationException(typeInfo.getClass()
					.getName());
		}
	}

	private MBuiltinLeafInfo<T, C> getTypeInfo(BLI info) {
		MBuiltinLeafInfo<T, C> builtinLeafInfo = builtinLeafInfos.get(info);
		if (builtinLeafInfo == null) {
			builtinLeafInfo = createBuiltinLeafInfo(info);
			builtinLeafInfos.put(info, builtinLeafInfo);
		}
		return builtinLeafInfo;
	}

	private MTypeInfo<T, C> getTypeInfo(EI info) {
		EPI p = (EPI) info.getProperty();
		TI contentType = (TI) info.getContentType();
		return getTypeInfo(p, contentType, p.isValueList(), p.getAdapter(),
				p.id(), p.getExpectedMimeType());
	}

	protected MClassInfo<T, C> getTypeInfo(CI info) {

		MClassInfo<T, C> classInfo = classInfos.get(info);
		if (classInfo == null) {
			classInfo = createClassInfo(info);
			classInfos.put(info, classInfo);
		}
		return classInfo;
	}

	private MEnumLeafInfo<T, C> getTypeInfo(ELI info) {
		MEnumLeafInfo<T, C> enumLeafInfo = enumLeafInfos.get(info);
		if (enumLeafInfo == null) {
			enumLeafInfo = createEnumLeafInfo(info);
			enumLeafInfos.put(info, enumLeafInfo);
		}
		return enumLeafInfo;

	}

	private void populateEnumLeafInfo(ELI info, MEnumLeafInfo<T, C> enumLeafInfo) {
		@SuppressWarnings("rawtypes")
		Iterable<? extends EnumConstant> _constants = info.getConstants();
		final Iterable<? extends EnumConstant<T, C>> enumConstants = (Iterable<? extends EnumConstant<T, C>>) _constants;
		for (EnumConstant<?, ?> enumConstant : enumConstants) {
			enumLeafInfo.addEnumConstantInfo(createEnumContantInfo(
					enumLeafInfo, (EC) enumConstant));
		}
	}

	protected MElementInfo<T, C> getElementInfo(EI info) {
		MElementInfo<T, C> elementInfo = elementInfos.get(info);
		if (elementInfo == null) {
			elementInfo = createElementInfo(info);
			elementInfos.put(info, elementInfo);
		}
		return elementInfo;

	}

	protected MClassInfo<T, C> createClassInfo(CI info) {
		return new CMClassInfo<T, C>(createClassInfoOrigin(info),
				info.getClazz(), getPackage(info), getContainer(info),
				getLocalName(info), createBaseTypeInfo(info),
				info.isElement() ? info.getElementName() : null,
				info.getTypeName());
	}

	private void populateClassInfo(CI info, MClassInfo<T, C> classInfo) {

		if (info.hasAttributeWildcard()) {
			classInfo.addProperty(createAnyAttributePropertyInfo(classInfo));
		}

		for (PropertyInfo<T, C> p : (List<? extends PropertyInfo<T, C>>) info
				.getProperties()) {
			classInfo.addProperty(createPropertyInfo(classInfo, (PI) p));
		}
	}

	protected MClassTypeInfo<T, C, ?> createBaseTypeInfo(CI info) {
		return info.getBaseClass() == null ? null : getTypeInfo((CI) info
				.getBaseClass());
	}

	private MPropertyInfo<T, C> createPropertyInfo(
			final MClassInfo<T, C> classInfo, PI p) {

		if (p instanceof AttributePropertyInfo) {
			final API api = (API) p;
			return createAttributePropertyInfo(classInfo, api);
		} else if (p instanceof ValuePropertyInfo) {
			final VPI vpi = (VPI) p;
			return createValuePropertyInfo(classInfo, vpi);
		} else if (p instanceof ElementPropertyInfo) {
			final EPI ep = (EPI) p;
			if (ep.getTypes().size() == 1) {
				return createElementPropertyInfo(classInfo, ep);
			} else {
				return createElementsPropertyInfo(classInfo, ep);

			}
		} else if (p instanceof ReferencePropertyInfo) {
			final RPI rp = (RPI) p;
			final Set<? extends Element<T, C>> elements = rp.getElements();
			if (elements.size() == 0
					&& rp.getWildcard() != null
					&& (rp.getWildcard().allowDom || rp.getWildcard().allowTypedObject)) {
				return createAnyElementPropertyInfo(classInfo, rp);
			} else if (elements.size() == 1) {
				return createElementRefPropertyInfo(classInfo, rp);
			} else {
				return createElementRefsPropertyInfo(classInfo, rp);
			}
		} else if (p instanceof MapPropertyInfo) {
			// System.out.println("Map property: " + p.getName());
			// MapPropertyInfo<T, C> mp = (MapPropertyInfo<T, C>) p;
			throw new UnsupportedOperationException();
		} else {
			throw new AssertionError();
		}

	}

	protected MPropertyInfo<T, C> createAttributePropertyInfo(
			final MClassInfo<T, C> classInfo, final API propertyInfo) {

		return new CMAttributePropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) propertyInfo), classInfo,
				propertyInfo.getName(), getTypeInfo(propertyInfo),
				propertyInfo.getXmlName(), propertyInfo.isRequired(),
				getDefaultValue(propertyInfo),
				getDefaultValueNamespaceContext(propertyInfo));
	}

	protected MPropertyInfo<T, C> createValuePropertyInfo(
			final MClassInfo<T, C> classInfo, final VPI propertyInfo) {
		return new CMValuePropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) propertyInfo), classInfo,
				propertyInfo.getName(), getTypeInfo(propertyInfo), null, null);
	}

	protected MPropertyInfo<T, C> createElementPropertyInfo(
			final MClassInfo<T, C> classInfo, final EPI ep) {
		final TR typeRef = (TR) ep.getTypes().get(0);
		return new CMElementPropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) ep), classInfo, ep.getName(),
				ep.isCollection() && !ep.isValueList(), ep.isRequired(),
				getTypeInfo(ep, typeRef), typeRef.getTagName(),
				ep.getXmlName(), typeRef.isNillable(),
				getDefaultValue(typeRef),
				getDefaultValueNamespaceContext(typeRef));
	}

	protected MPropertyInfo<T, C> createElementsPropertyInfo(
			final MClassInfo<T, C> classInfo, final EPI ep) {
		List<? extends TR> types = (List<? extends TR>) ep.getTypes();
		final Collection<MElementTypeRef<T, C>> typedElements = new ArrayList<MElementTypeRef<T, C>>(
				types.size());
		for (TR typeRef : types) {
			typedElements.add(new CMElementTypeRef<T, C>(
					createElementTypeRefOrigin(ep, typeRef), typeRef
							.getTagName(), getTypeInfo(ep, typeRef), typeRef
							.isNillable(), getDefaultValue(typeRef),
					getDefaultValueNamespaceContext(typeRef)));
		}
		return new CMElementsPropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) ep), classInfo, ep.getName(),
				ep.isCollection() && !ep.isValueList(), ep.isRequired(),
				typedElements, ep.getXmlName());
	}

	protected MPropertyInfo<T, C> createAnyElementPropertyInfo(
			final MClassInfo<T, C> classInfo, final RPI rp) {
		return new CMAnyElementPropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) rp), classInfo, rp.getName(),
				rp.isCollection(), rp.isRequired(), rp.isMixed(),
				rp.getWildcard().allowDom, rp.getWildcard().allowTypedObject);
	}

	protected MPropertyInfo<T, C> createElementRefPropertyInfo(
			final MClassInfo<T, C> classInfo, final RPI rp) {
		final Element<T, C> element = rp.getElements().iterator().next();
		return new CMElementRefPropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) rp), classInfo, rp.getName(),
				rp.isCollection(), rp.isRequired(), getTypeInfo(rp, element),
				element.getElementName(), rp.getXmlName(),

				rp.isMixed(), rp.getWildcard() == null ? false
						: rp.getWildcard().allowDom,
				rp.getWildcard() == null ? true
						: rp.getWildcard().allowTypedObject,
				getDefaultValue(element),
				getDefaultValueNamespaceContext(element));
	}

	protected MPropertyInfo<T, C> createElementRefsPropertyInfo(
			final MClassInfo<T, C> classInfo, final RPI rp) {
		final List<MElement<T, C>> typedElements = new ArrayList<MElement<T, C>>();
		for (Element<T, C> e : rp.getElements()) {
			final E element = (E) e;
			typedElements.add(new CMElement<T, C>(createElementOrigin(element),
					element.getElementName(), getTypeInfo(rp, element), true,
					getDefaultValue(element),
					getDefaultValueNamespaceContext(element)));
		}
		return new CMElementRefsPropertyInfo<T, C>(
				createPropertyInfoOrigin((PI) rp), classInfo, rp.getName(),
				rp.isCollection(), rp.isRequired(), typedElements,
				rp.getXmlName(), rp.isMixed(), rp.getWildcard() == null ? false
						: rp.getWildcard().allowDom,
				rp.getWildcard() == null ? true
						: rp.getWildcard().allowTypedObject);
	}

	protected CMAnyAttributePropertyInfo<T, C> createAnyAttributePropertyInfo(
			final MClassInfo<T, C> classInfo) {
		return new CMAnyAttributePropertyInfo<T, C>(
				createAnyAttributePropertyInfoOrigin(), classInfo,
				"otherAttributes");
	}

	protected MTypeInfo<T, C> getTypeInfo(final ValuePropertyInfo<T, C> vp) {
		return getTypeInfo(vp, (TI) vp.ref().iterator().next(),
				vp.isCollection(), vp.getAdapter(), vp.id(),
				vp.getExpectedMimeType());
	}

	protected MTypeInfo<T, C> getTypeInfo(final AttributePropertyInfo<T, C> ap) {
		return getTypeInfo(ap, (TI) ap.ref().iterator().next(),
				ap.isCollection(), ap.getAdapter(), ap.id(),
				ap.getExpectedMimeType());
	}

	protected MTypeInfo<T, C> getTypeInfo(final ElementPropertyInfo<T, C> ep,
			final TR typeRef) {
		return getTypeInfo(ep, (TI) typeRef.getTarget(),

		ep.isValueList(), ep.getAdapter(), ep.id(), ep.getExpectedMimeType());
	}

	protected MTypeInfo<T, C> getTypeInfo(final ReferencePropertyInfo<T, C> rp,
			Element<T, C> element) {
		return getTypeInfo(rp, (TI) element, false, rp.getAdapter(), rp.id(),
				rp.getExpectedMimeType());
	}

	private String getDefaultValue(Element<T, C> element) {
		if (element instanceof ElementInfo) {
			final ElementInfo<T, C> elementInfo = (ElementInfo<T, C>) element;
			final ElementPropertyInfo<T, C> property = elementInfo
					.getProperty();
			if (property != null) {
				final List<? extends TR> types = (List<? extends TR>) property.getTypes();
				if (types.size() == 1) {
					final TR typeRef = types.get(0);
					return getDefaultValue(typeRef);
				}
			}
		}
		return null;
	}

	private NamespaceContext getDefaultValueNamespaceContext(
			Element<T, C> element) {
		if (element instanceof ElementInfo) {
			final ElementInfo<T, C> elementInfo = (ElementInfo<T, C>) element;
			final ElementPropertyInfo<T, C> property = elementInfo
					.getProperty();
			if (property != null) {
				final List<? extends TypeRef<T, C>> types = property.getTypes();
				if (types.size() == 1) {
					final TypeRef<T, C> typeRef = types.get(0);
					return getDefaultValueNamespaceContext(typeRef);
				}
			}
		}
		return null;
	}

	protected abstract MPackageInfo getPackage(CI info);

	protected abstract String getLocalName(CI info);

	protected abstract MClassInfo<T, C> getScope(CI info);

	protected abstract MPackageInfo getPackage(ELI info);

	protected abstract String getLocalName(ELI info);

	protected abstract String getLocalName(EI info);

	protected abstract MPackageInfo getPackage(EI info);

	protected abstract MContainer getContainer(CI info);

	protected abstract MContainer getContainer(EI info);

	protected abstract MContainer getContainer(ELI info);

	//

	protected MBuiltinLeafInfo<T, C> createBuiltinLeafInfo(BLI info) {
		return new CMBuiltinLeafInfo<T, C>(createBuiltinLeafInfoOrigin(info),
				info.getType(), info.getTypeName());
	}

	protected MEnumLeafInfo<T, C> createEnumLeafInfo(final ELI info) {
		final TI baseType = (TI) info.getBaseType();
		return new CMEnumLeafInfo<T, C>(createEnumLeafInfoOrigin(info),
				info.getClazz(), getPackage(info), getContainer(info),
				getLocalName(info), getTypeInfo(baseType),
				info.getElementName(), info.getTypeName());
	}

	protected CMEnumConstantInfo<T, C> createEnumContantInfo(
			MEnumLeafInfo<T, C> enumLeafInfo, EC enumConstant) {
		return new CMEnumConstantInfo<T, C>(
				createEnumConstantInfoOrigin(enumConstant), enumLeafInfo,
				enumConstant.getLexicalValue());
	}

	protected MElementInfo<T, C> createElementInfo(EI element) {
		final CI scopeCI = (CI) element.getScope();
		final MClassInfo<T, C> scope = element.getScope() == null ? null
				: getTypeInfo(scopeCI);
		final QName substitutionHead = element.getSubstitutionHead() == null ? null
				: element.getSubstitutionHead().getElementName();
		final MElementInfo<T, C> elementInfo = new CMElementInfo<T, C>(
				createElementInfoOrigin(element), getPackage(element),
				getContainer(element), getLocalName(element),
				element.getElementName(), scope, getTypeInfo(element),
				substitutionHead, getDefaultValue(element),
				getDefaultValueNamespaceContext(element));
		return elementInfo;
	}

	protected MTypeInfo<T, C> createWildcardTypeInfo(WTI info) {
		return new CMWildcardTypeInfo<T, C>(createWildcardTypeInfoOrigin(info),
				info.getType());
	}

	protected MModelInfoOrigin createModelInfoOrigin(TIS info) {
		return new CMModelInfoOrigin<T, C, TIS>(info);
	}

	protected MBuiltinLeafInfoOrigin createBuiltinLeafInfoOrigin(BLI info) {
		return new CMBuiltinLeafInfoOrigin<T, C, BLI>(info);
	}

	protected MClassInfoOrigin createClassInfoOrigin(CI info) {
		return new CMClassInfoOrigin<T, C, CI>(info);
	}

	protected MPropertyInfoOrigin createAnyAttributePropertyInfoOrigin() {
		return new CMAnyAttributePropertyInfoOrigin();
	}

	protected MPropertyInfoOrigin createPropertyInfoOrigin(PI info) {
		return new CMPropertyInfoOrigin<T, C, PI>(info);
	}

	protected MElementOrigin createElementOrigin(E info) {
		return new CMElementOrigin<T, C, E>(info);
	}

	protected MElementTypeRefOrigin createElementTypeRefOrigin(EPI ep,
			TR typeRef) {
		return new CMElementTypeRefOrigin<T, C, EPI, TR>(ep, typeRef);
	}

	protected MElementInfoOrigin createElementInfoOrigin(EI info) {
		return new CMElementInfoOrigin<T, C, EI>(info);
	}

	protected MEnumLeafInfoOrigin createEnumLeafInfoOrigin(ELI info) {
		return new CMEnumLeafInfoOrigin<T, C, ELI>(info);
	}

	protected MEnumConstantInfoOrigin createEnumConstantInfoOrigin(EC info) {
		return new CMEnumConstantInfoOrigin<T, C, EC>(info);
	}

	protected MWildcardTypeInfoOrigin createWildcardTypeInfoOrigin(WTI info) {
		return new CMWildcardTypeInfoOrigin<T, C, WTI>(info);
	}

	protected abstract T createListType(T elementType);

	/**
	 * Returns Java class for the reference type or null if it can't be found.
	 * 
	 * @param referencedType
	 *            referenced type.
	 * @return Java class for the reference type or null.
	 */
	protected abstract Class<?> loadClass(T referencedType);

	protected abstract String getDefaultValue(API propertyInfo);

	protected abstract NamespaceContext getDefaultValueNamespaceContext(
			API propertyInfo);

	protected abstract String getDefaultValue(TypeRef<T, C> typeRef);

	protected abstract NamespaceContext getDefaultValueNamespaceContext(
			TypeRef<T, C> typeRef);
}
