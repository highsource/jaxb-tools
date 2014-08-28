package org.jvnet.jaxb2_commons.xjc.model.concrete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMClassInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMElementInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMModelInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
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
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.CWildcardTypeInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMInfoFactory
		extends
		CMInfoFactory<NType, NClass, Model, CTypeInfo, CBuiltinLeafInfo, CElementInfo, CEnumLeafInfo, CEnumConstant, CClassInfo, CPropertyInfo, CAttributePropertyInfo, CValuePropertyInfo, CElementPropertyInfo, CReferencePropertyInfo, CWildcardTypeInfo> {

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

	protected MClassRef<NType, NClass> createClassRef(Class<?> _class) {
		return new CMClassRef<NType, NClass>(getClazz(_class),
				getPackage(_class), getContainer(_class), getLocalName(_class));
	}

	protected MClassRef<NType, NClass> createClassRef(CClassRef info) {
		return new CMClassRef<NType, NClass>(getClazz(info), getPackage(info),
				getContainer(info), getLocalName(info));
	}

	@Override
	protected MTypeInfo<NType, NClass> getTypeInfo(CTypeInfo typeInfo) {
		if (typeInfo instanceof CClassRef) {
			return createClassRef((CClassRef) typeInfo);
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

	private MPackageInfo getPackage(final Class _class) {
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
			return createClassRef(enclosingClass);
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
}
