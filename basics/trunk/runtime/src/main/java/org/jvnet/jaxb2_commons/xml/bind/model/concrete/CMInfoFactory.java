package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.util.ArrayList;
import java.util.Collection;
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
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMAnyAttributePropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMBuiltinLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMWildcardTypeInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MBuiltinLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
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

public abstract class CMInfoFactory<T, C, TIS extends TypeInfoSet<T, C, ?, ?>,
//
TI extends TypeInfo<T, C>,
//
BLI extends BuiltinLeafInfo<T, C>,
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
WTI extends WildcardTypeInfo<T, C>> {

	private final Map<BLI, MBuiltinLeafInfo> builtinLeafInfos = new IdentityHashMap<BLI, MBuiltinLeafInfo>();

	private final Map<CI, MClassInfo> classInfos = new IdentityHashMap<CI, MClassInfo>();

	private final Map<ELI, MEnumLeafInfo> enumLeafInfos = new IdentityHashMap<ELI, MEnumLeafInfo>();

	private final Map<EI, MElementInfo> elementInfos = new IdentityHashMap<EI, MElementInfo>();

	private final TIS typeInfoSet;

	public CMInfoFactory(TIS typeInfoSet) {
		Validate.notNull(typeInfoSet);
		this.typeInfoSet = typeInfoSet;

	}

	@SuppressWarnings("unchecked")
	public MModelInfo createModel() {
		final CMModel<T, C> model = new CMModel<T, C>(
				createModelInfoOrigin(typeInfoSet));

		Collection<? extends BuiltinLeafInfo<T, C>> builtins = typeInfoSet
				.builtins().values();
		for (BuiltinLeafInfo<T, C> builtinLeafInfo : builtins) {
			model.addBuiltinLeafInfo(getTypeInfo((BLI) builtinLeafInfo));
		}

		Collection<? extends ClassInfo<T, C>> beans = typeInfoSet.beans()
				.values();
		for (ClassInfo<T, C> classInfo : beans) {
			model.addClassInfo(getTypeInfo((CI) classInfo));
		}

		Collection<? extends EnumLeafInfo<T, C>> enums = typeInfoSet.enums()
				.values();
		for (EnumLeafInfo<T, C> enumLeafInfo : enums) {
			model.addEnumLeafInfo(getTypeInfo((ELI) enumLeafInfo));
		}

		Iterable<? extends ElementInfo<T, C>> elements = typeInfoSet
				.getAllElements();
		for (ElementInfo<T, C> element : elements) {
			model.addElementInfo(getElementInfo((EI) element));
		}
		return model;

	}

	protected MTypeInfo getTypeInfo(PropertyInfo<T, C> propertyInfo,
			TI typeInfo, boolean list, Adapter<T, C> adapter, ID id,
			MimeType mimeType) {
		final MTypeInfo ti = getTypeInfo(typeInfo);
		if (!list) {
			return ti;
		} else {
			return new CMList(ti);
		}
	}

	@SuppressWarnings("unchecked")
	protected MTypeInfo getTypeInfo(TI typeInfo) {
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
			throw new UnsupportedOperationException();
		}
	}

	private MBuiltinLeafInfo getTypeInfo(BLI typeInfo) {
		MBuiltinLeafInfo builtinLeafInfo = builtinLeafInfos.get(typeInfo);
		if (builtinLeafInfo == null) {
			builtinLeafInfo = createBuiltinLeafInfo(typeInfo);
			builtinLeafInfos.put(typeInfo, builtinLeafInfo);
			return builtinLeafInfo;
		}
		return builtinLeafInfo;
	}

	private MTypeInfo getTypeInfo(EI info) {
		@SuppressWarnings("unchecked")
		EPI p = (EPI) info.getProperty();
		@SuppressWarnings("unchecked")
		TI contentType = (TI) info.getContentType();
		return getTypeInfo(p, contentType, p.isValueList(), p.getAdapter(),
				p.id(), p.getExpectedMimeType());
	}

	protected MClassInfo getTypeInfo(CI info) {

		MClassInfo classInfo = classInfos.get(info);

		if (classInfo == null) {

			classInfo = createClassInfo(info);
			classInfos.put(info, classInfo);

			if (info.hasAttributeWildcard()) {
				classInfo
						.addProperty(createAnyAttributePropertyInfo(classInfo));
			}

			for (PropertyInfo<T, C> p : (List<? extends PropertyInfo<T, C>>) info
					.getProperties()) {
				classInfo.addProperty(createPropertyInfo(classInfo, (PI) p));
			}
		}
		return classInfo;
	}

	private MEnumLeafInfo getTypeInfo(ELI info) {
		MEnumLeafInfo enumLeafInfo = enumLeafInfos.get(info);
		if (enumLeafInfo == null) {
			enumLeafInfo = createEnumLeafInfo(info);
			enumLeafInfos.put(info, enumLeafInfo);

			@SuppressWarnings("rawtypes")
			Iterable<? extends EnumConstant> _constants = info.getConstants();
			@SuppressWarnings("unchecked")
			final Iterable<? extends EnumConstant<T, C>> enumConstants = (Iterable<? extends EnumConstant<T, C>>) _constants;
			for (EnumConstant<?, ?> enumConstant : enumConstants) {
				enumLeafInfo.addEnumConstantInfo(createEnumContantInfo(
						enumLeafInfo, (EC) enumConstant));
			}
		}
		return enumLeafInfo;

	}

	private MElementInfo getElementInfo(EI info) {
		MElementInfo mElementInfo = elementInfos.get(info);
		if (mElementInfo == null) {
			mElementInfo = createElementInfo(info);
			elementInfos.put(info, mElementInfo);
		}
		return mElementInfo;

	}

	protected MClassInfo createClassInfo(CI info) {
		return new CMClassInfo(createClassInfoOrigin(info), getPackage(info),
				getLocalName(info), info.getBaseClass() == null ? null
						: getTypeInfo((CI) info.getBaseClass()),
				info.isElement() ? info.getElementName() : null);
	}

	private MPropertyInfo createPropertyInfo(final MClassInfo classInfo, PI p) {

		if (p instanceof AttributePropertyInfo) {
			@SuppressWarnings("unchecked")
			final API api = (API) p;
			return createAttributePropertyInfo(classInfo, api);
		} else if (p instanceof ValuePropertyInfo) {
			@SuppressWarnings("unchecked")
			final VPI vpi = (VPI) p;
			return createValuePropertyInfo(classInfo, vpi);
		} else if (p instanceof ElementPropertyInfo) {
			@SuppressWarnings("unchecked")
			final EPI ep = (EPI) p;
			if (ep.getTypes().size() == 1) {
				return createElementPropertyInfo(classInfo, ep);
			} else {
				return createElementsPropertyInfo(classInfo, ep);

			}
		} else if (p instanceof ReferencePropertyInfo) {
			@SuppressWarnings("unchecked")
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

	protected MPropertyInfo createAttributePropertyInfo(
			final MClassInfo classInfo, final API propertyInfo) {
		return new CMAttributePropertyInfo(
				createPropertyInfoOrigin((PI) propertyInfo), classInfo,
				propertyInfo.getName(), getTypeInfo(propertyInfo),
				propertyInfo.getXmlName());
	}

	protected MPropertyInfo createValuePropertyInfo(final MClassInfo classInfo,
			final VPI propertyInfo) {
		return new CMValuePropertyInfo(
				createPropertyInfoOrigin((PI) propertyInfo), classInfo,
				propertyInfo.getName(), getTypeInfo(propertyInfo));
	}

	protected MPropertyInfo createElementPropertyInfo(
			final MClassInfo classInfo, final EPI ep) {
		final TypeRef<T, C> typeRef = ep.getTypes().get(0);
		return new CMElementPropertyInfo(createPropertyInfoOrigin((PI) ep),
				classInfo, ep.getName(),
				ep.isCollection() && !ep.isValueList(),
				getTypeInfo(ep, typeRef), typeRef.getTagName(), ep.getXmlName());
	}

	protected MPropertyInfo createElementsPropertyInfo(
			final MClassInfo classInfo, final EPI ep) {
		List<? extends TypeRef<T, C>> types = ep.getTypes();
		final Collection<MElementTypeInfo> typedElements = new ArrayList<MElementTypeInfo>(
				types.size());
		for (TypeRef<T, C> typeRef : types) {
			typedElements.add(new CMElementTypeInfo(typeRef.getTagName(),
					getTypeInfo(ep, typeRef)));
		}
		return new CMElementsPropertyInfo(createPropertyInfoOrigin((PI) ep),
				classInfo, ep.getName(),
				ep.isCollection() && !ep.isValueList(), typedElements,
				ep.getXmlName());
	}

	protected MPropertyInfo createAnyElementPropertyInfo(
			final MClassInfo classInfo, final RPI rp) {
		return new CMAnyElementPropertyInfo(createPropertyInfoOrigin((PI) rp),
				classInfo, rp.getName(), rp.isCollection(), rp.isMixed(),
				rp.getWildcard().allowDom, rp.getWildcard().allowTypedObject);
	}

	protected MPropertyInfo createElementRefPropertyInfo(
			final MClassInfo classInfo, final RPI rp) {
		final Element<T, C> element = rp.getElements().iterator().next();
		return new CMElementRefPropertyInfo(createPropertyInfoOrigin((PI) rp),
				classInfo, rp.getName(), rp.isCollection(), getTypeInfo(rp,
						element), element.getElementName(), rp.getXmlName(),

				rp.isMixed(), rp.getWildcard() == null ? false
						: rp.getWildcard().allowDom,
				rp.getWildcard() == null ? false
						: rp.getWildcard().allowTypedObject);
	}

	protected MPropertyInfo createElementRefsPropertyInfo(
			final MClassInfo classInfo, final RPI rp) {
		final List<MElementTypeInfo> typedElements = new ArrayList<MElementTypeInfo>();
		for (Element<T, C> element : rp.getElements()) {
			typedElements.add(new CMElementTypeInfo(element.getElementName(),
					getTypeInfo(rp, element)));
		}
		return new CMElementRefsPropertyInfo(createPropertyInfoOrigin((PI) rp),
				classInfo, rp.getName(), rp.isCollection(), typedElements,
				rp.getXmlName(), rp.isMixed(), rp.getWildcard() == null ? false
						: rp.getWildcard().allowDom,
				rp.getWildcard() == null ? false
						: rp.getWildcard().allowTypedObject);
	}

	protected CMAnyAttributePropertyInfo createAnyAttributePropertyInfo(
			final MClassInfo classInfo) {
		return new CMAnyAttributePropertyInfo(
				createAnyAttributePropertyInfoOrigin(), classInfo,
				"otherAttributes");
	}

	protected MTypeInfo getTypeInfo(final ValuePropertyInfo<T, C> vp) {
		return getTypeInfo(vp, (TI) vp.ref().iterator().next(),
				vp.isCollection(), vp.getAdapter(), vp.id(),
				vp.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final AttributePropertyInfo<T, C> ap) {
		return getTypeInfo(ap, (TI) ap.ref().iterator().next(),
				ap.isCollection(), ap.getAdapter(), ap.id(),
				ap.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final ElementPropertyInfo<T, C> ep,
			final TypeRef<T, C> typeRef) {
		return getTypeInfo(ep, (TI) typeRef.getTarget(),

		ep.isValueList(), ep.getAdapter(), ep.id(), ep.getExpectedMimeType());
	}

	protected MTypeInfo getTypeInfo(final ReferencePropertyInfo<T, C> rp,
			Element<T, C> element) {
		return getTypeInfo(rp, (TI) element, false, rp.getAdapter(), rp.id(),
				rp.getExpectedMimeType());
	}

	protected abstract MPackageInfo getPackage(CI info);

	protected abstract String getLocalName(CI info);

	protected abstract MPackageInfo getPackage(ELI info);

	protected abstract String getLocalName(ELI info);

	protected abstract MPackageInfo getPackage(EI info);

	//

	protected MBuiltinLeafInfo createBuiltinLeafInfo(BLI info) {
		return new CMBuiltinLeafInfo(createBuiltinLeafInfoOrigin(info),
				info.getTypeName());
	}

	protected MEnumLeafInfo createEnumLeafInfo(final ELI info) {
		@SuppressWarnings("unchecked")
		final TI baseType = (TI) info.getBaseType();
		return new CMEnumLeafInfo(createEnumLeafInfoOrigin(info),
				getPackage(info), getLocalName(info), getTypeInfo(baseType),
				info.getElementName());
	}

	protected CMEnumConstantInfo createEnumContantInfo(
			MEnumLeafInfo enumLeafInfo, EC enumConstant) {
		return new CMEnumConstantInfo(
				createEnumConstantInfoOrigin(enumConstant), enumLeafInfo,
				enumConstant.getLexicalValue());
	}

	protected MElementInfo createElementInfo(EI element) {
		@SuppressWarnings("unchecked")
		final CI scopeCI = (CI) element.getScope();
		final MClassInfo scope = element.getScope() == null ? null
				: getTypeInfo(scopeCI);
		final QName substitutionHead = element.getSubstitutionHead() == null ? null
				: element.getSubstitutionHead().getElementName();
		final MElementInfo elementInfo = new CMElementInfo(
				createElementInfoOrigin(element), getPackage(element),
				element.getElementName(), scope, getTypeInfo(element),
				substitutionHead);
		return elementInfo;
	}

	protected MTypeInfo createWildcardTypeInfo(WTI info) {
		return new CMWildcardTypeInfo(createWildcardTypeInfoOrigin(info));
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

}
