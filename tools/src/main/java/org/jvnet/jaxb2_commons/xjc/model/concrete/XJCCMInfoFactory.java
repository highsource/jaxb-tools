package org.jvnet.jaxb2_commons.xjc.model.concrete;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMClassInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMClassRefOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMElementInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMElementOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMElementTypeRefOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMModelInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassRefOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementTypeRefOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CClassInfoParent.Visitor;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.CWildcardTypeInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIEnum;
import com.sun.tools.xjc.util.NamespaceContextAdapter;
import com.sun.xml.bind.v2.model.core.TypeRef;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XmlString;

public class XJCCMInfoFactory
		extends
		CMInfoFactory<NType, NClass, Model, CTypeInfo, CBuiltinLeafInfo, CElement, CElementInfo, CEnumLeafInfo, CEnumConstant, CClassInfo, CPropertyInfo, CAttributePropertyInfo, CValuePropertyInfo, CElementPropertyInfo, CReferencePropertyInfo, CWildcardTypeInfo, CTypeRef> {

	private final Map<CClassRef, MClassRef<NType, NClass>> classRefs =

	new IdentityHashMap<CClassRef, MClassRef<NType, NClass>>();

	public XJCCMInfoFactory(Model model) {
		super(model);
	}

	protected NClass getClazz(CClassRef info) {
		return info;
	}

	protected NClass getClazz(final Class<?> _clas) {
		return new NClass() {

			@Override
			public boolean isBoxedType() {
				return false;
			}

			@Override
			public String fullName() {
				return _clas.getName();
			}

			@Override
			public JClass toType(Outline o, Aspect aspect) {
				return o.getCodeModel().ref(_clas);
			}

			@Override
			public boolean isAbstract() {
				return false;
			}
		};
	}

	protected NClass getClazz(final String className) {
		return new NClass() {

			@Override
			public boolean isBoxedType() {
				return false;
			}

			@Override
			public String fullName() {
				return className;
			}

			@Override
			public JClass toType(Outline o, Aspect aspect) {
				return o.getCodeModel().ref(className);
			}

			@Override
			public boolean isAbstract() {
				return false;
			}
		};
	}

	// protected MClassRef<NType, NClass> createClassRef(Model model,
	// Class<?> _class) {
	//
	// final BIEnum decl = new BIEnum();
	// decl.ref = _class.getName();
	// final CClassRef baseClass = new CClassRef(model,
	// null, decl, new CCustomizations());
	//
	// return new CMClassRef<NType, NClass>(
	// getClazz(_class), _class,
	// getPackage(_class), getContainer(model, _class), getLocalName(_class));
	// }

	protected MClassRef<NType, NClass> getTypeInfo(CClassRef info) {

		MClassRef<NType, NClass> classInfo = classRefs.get(info);

		if (classInfo == null) {

			classInfo = createClassRef(info);
			classRefs.put(info, classInfo);
		}
		return classInfo;
	}

	protected MClassRef<NType, NClass> createClassRef(CClassRef info) {
		final NClass targetType = getClazz(info);
		return new CMClassRef<NType, NClass>(createClassRefOrigin(info),
				targetType, loadClass(targetType), getPackage(info),
				getContainer(info), getLocalName(info));
	}

	@Override
	protected MTypeInfo<NType, NClass> getTypeInfo(CTypeInfo typeInfo) {
		if (typeInfo instanceof CClassRef) {
			return getTypeInfo((CClassRef) typeInfo);
		} else {
			return super.getTypeInfo(typeInfo);
		}
	}

	@Override
	protected MPackageInfo getPackage(CClassInfo info) {
		return getPackage(info.parent());
	}

	protected MPackageInfo getPackage(CClassRef info) {
		final String fullName = info.fullName();
		return getPackage(fullName);
	}

	private MPackageInfo getPackage(final String fullName) {
		try {
			final Class<?> _class = Class.forName(fullName);
			return getPackage(_class);
		} catch (ClassNotFoundException cnfex) {
			final String packageName;
			final int lastIndexOfDot = fullName.lastIndexOf('.');
			if (lastIndexOfDot != -1) {
				packageName = fullName.substring(0, lastIndexOfDot);
			} else {
				packageName = "";
			}
			return new CMPackageInfo(new CMPackageInfoOrigin(), packageName);
		}
	}

	private MPackageInfo getPackage(final Class<?> _class) {
		final Package _package = _class.getPackage();
		return new CMPackageInfo(new CMPackageInfoOrigin(), _package.getName());
	}

	@Override
	protected MPackageInfo getPackage(CEnumLeafInfo info) {
		return getPackage(info.parent);
	}

	@Override
	protected MPackageInfo getPackage(CElementInfo info) {
		return getPackage(info.parent);
	}

	@Override
	protected MContainer getContainer(CClassInfo info) {
		final CClassInfoParent parent = info.parent();
		return parent == null ? null : getContainer(parent);
	}

	@Override
	protected MContainer getContainer(CElementInfo info) {
		final CClassInfoParent parent = info.parent;
		return parent == null ? null : getContainer(parent);
	}

	@Override
	protected MContainer getContainer(CEnumLeafInfo info) {
		final CClassInfoParent parent = info.parent;
		return parent == null ? null : getContainer(parent);
	}

	protected MContainer getContainer(CClassRef info) {
		final String fullName = info.fullName();
		try {
			final Class<?> _class = Class.forName(fullName);
			return getContainer(_class);
		} catch (ClassNotFoundException cnfex) {
			return getPackage(info);
		}
	}

	private MContainer getContainer(final Class<?> _class) {
		final Class<?> enclosingClass = _class.getEnclosingClass();
		if (enclosingClass == null) {
			return getPackage(_class);
		} else {
			final BIEnum decl = new BIEnum();
			decl.ref = _class.getName();
			final CClassRef enclosingClassRef = new CClassRef(getTypeInfoSet(),
					null, decl, new CCustomizations());
			return createClassRef(enclosingClassRef);
		}
	}

	private final Map<String, MPackageInfo> packages = new HashMap<String, MPackageInfo>();

	private MContainer getContainer(CClassInfoParent parent) {
		return parent.accept(new Visitor<MContainer>() {

			public MContainer onBean(CClassInfo bean) {
				return getTypeInfo(bean);
			}

			public MContainer onPackage(JPackage pkg) {
				return getPackage(pkg);
			}

			public MContainer onElement(CElementInfo element) {
				return getElementInfo(element);
			}
		});
	}

	private MPackageInfo getPackage(CClassInfoParent parent) {

		return parent.accept(new Visitor<MPackageInfo>() {

			public MPackageInfo onBean(CClassInfo bean) {
				return getPackage(bean.parent());
			}

			public MPackageInfo onPackage(JPackage pkg) {
				return getPackage(pkg);
			}

			public MPackageInfo onElement(CElementInfo element) {
				return getPackage(element.parent);
			}
		});

	}

	@Override
	protected MClassInfo<NType, NClass> getScope(CClassInfo info) {
		return info.getScope() == null ? null : getTypeInfo(info.getScope());
	}

	@Override
	protected String getLocalName(CClassInfo info) {
		return info.shortName;
	}

	protected String getLocalName(CClassRef info) {
		final String fullName = info.fullName();
		try {
			final Class<?> _class = Class.forName(fullName);
			return getLocalName(_class);
		} catch (ClassNotFoundException cnfex) {
			return getLocalName(fullName);
		}
	}

	private String getLocalName(final String fullName) {
		final int lastIndexOfDollar = fullName.lastIndexOf('$');
		if (lastIndexOfDollar != -1) {
			return fullName.substring(lastIndexOfDollar + 1);
		}
		final int lastIndexOfDot = fullName.lastIndexOf('.');
		if (lastIndexOfDot != -1) {
			return fullName.substring(lastIndexOfDot + 1);
		}
		return fullName;
	}

	private String getLocalName(final Class<?> _class) {
		return _class.getSimpleName();
	}

	@Override
	protected String getLocalName(CEnumLeafInfo info) {
		return info.shortName;
	}

	@Override
	protected String getLocalName(CElementInfo info) {
		return info.shortName();
	}

	@Override
	protected MModelInfoOrigin createModelInfoOrigin(Model info) {
		return new XJCCMModelInfoOrigin(info);
	}

	protected MPackageInfoOrigin createPackageInfoOrigin(JPackage info) {
		return new XJCCMPackageInfoOrigin(info);
	}

	protected MClassInfoOrigin createClassInfoOrigin(CClassInfo info) {
		return new XJCCMClassInfoOrigin(info);
	}

	@Override
	protected MElementOrigin createElementOrigin(CElement info) {
		return new XJCCMElementOrigin(info);
	}

	@Override
	protected MElementTypeRefOrigin createElementTypeRefOrigin(
			CElementPropertyInfo ep, CTypeRef typeRef) {
		return new XJCCMElementTypeRefOrigin(ep, typeRef);
	}

	protected MClassRefOrigin createClassRefOrigin(CClassRef info) {
		return new XJCCMClassRefOrigin(info);
	}

	@Override
	protected MPropertyInfoOrigin createPropertyInfoOrigin(CPropertyInfo info) {
		return new XJCCMPropertyInfoOrigin(info);
	}

	@Override
	protected MElementInfoOrigin createElementInfoOrigin(CElementInfo info) {
		return new XJCCMElementInfoOrigin(info);
	}

	@Override
	protected MEnumLeafInfoOrigin createEnumLeafInfoOrigin(CEnumLeafInfo info) {
		return new XJCCMEnumLeafInfoOrigin(info);
	}

	@Override
	protected MEnumConstantInfoOrigin createEnumConstantInfoOrigin(
			CEnumConstant info) {
		return new XJCCMEnumConstantInfoOrigin(info);
	}

	@Override
	protected NType createListType(final NType elementType) {

		return new NClass() {

			public boolean isBoxedType() {
				return false;
			}

			public String fullName() {
				return List.class.getName();
			}

			public JClass toType(Outline o, Aspect aspect) {
				return o.getCodeModel().ref(List.class)
						.narrow(elementType.toType(o, aspect).boxify());
			}

			public boolean isAbstract() {
				return false;
			}
		};
	}

	private MPackageInfo getPackage(JPackage pkg) {
		String packageName = pkg.name();
		MPackageInfo _package = packages.get(packageName);
		if (_package == null) {
			_package = new CMPackageInfo(createPackageInfoOrigin(pkg),
					packageName);
			packages.put(packageName, _package);
		}
		return _package;
	}

	@Override
	protected MClassTypeInfo<NType, NClass, ?> createBaseTypeInfo(
			CClassInfo info) {
		if (info.getBaseClass() != null) {
			return getTypeInfo(info.getBaseClass());
		} else if (info.getRefBaseClass() != null) {
			return getTypeInfo(info.getRefBaseClass());
		} else {
			return null;
		}
	}

	@Override
	protected Class<?> loadClass(NType referencedType) {
		final String name = referencedType.fullName();
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException cnfex) {
			return null;
		}
	}

	private XSAttributeUse getAttributeUse(CAttributePropertyInfo propertyInfo) {
		final XSComponent schemaComponent = propertyInfo.getSchemaComponent();
		if (schemaComponent instanceof XSAttributeUse) {
			return (XSAttributeUse) schemaComponent;
		} else {
			return null;
		}
	}

	protected String getDefaultValue(CAttributePropertyInfo propertyInfo) {

		final XSAttributeUse attributeUse = getAttributeUse(propertyInfo);
		if (attributeUse != null) {
			final XmlString defaultValue = attributeUse.getDefaultValue();
			if (defaultValue != null) {
				return defaultValue.value;
			}
		}
		return null;
	}

	protected NamespaceContext getDefaultValueNamespaceContext(
			CAttributePropertyInfo propertyInfo) {
		final XSAttributeUse attributeUse = getAttributeUse(propertyInfo);
		if (attributeUse != null) {
			final XmlString defaultValue = attributeUse.getDefaultValue();
			if (defaultValue != null) {
				return new NamespaceContextAdapter(defaultValue);
			}
		}
		return null;

	}

	@Override
	protected String getDefaultValue(TypeRef<NType, NClass> typeRef) {
		return typeRef == null ? null : typeRef.getDefaultValue();
	}

	@Override
	protected NamespaceContext getDefaultValueNamespaceContext(
			TypeRef<NType, NClass> typeRef) {
		if (typeRef instanceof CTypeRef) {
			final CTypeRef cTypeRef = (CTypeRef) typeRef;
			return new NamespaceContextAdapter(cTypeRef.defaultValue);
		} else {
			return null;
		}
	}
}
