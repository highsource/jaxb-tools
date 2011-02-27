package org.jvnet.jaxb2_commons.xjc.model.concrete;

import java.util.HashMap;
import java.util.Map;

import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMClassInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMElementInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMModelInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.XJCCMPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MClassInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MElementInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MModelInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPackageInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CClassInfoParent.Visitor;
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

public class XJCCMInfoFactory
		extends
		CMInfoFactory<NType, NClass, Model, CTypeInfo, CBuiltinLeafInfo, CElementInfo, CEnumLeafInfo, CEnumConstant, CClassInfo, CPropertyInfo, CAttributePropertyInfo, CValuePropertyInfo, CElementPropertyInfo, CReferencePropertyInfo, CWildcardTypeInfo> {

	public XJCCMInfoFactory(Model model) {
		super(model);
	}

	@Override
	protected MPackageInfo getPackage(CClassInfo info) {
		return getPackage(info.parent());
	}

	@Override
	protected MPackageInfo getPackage(CEnumLeafInfo info) {
		return getPackage(info.parent);
	}

	@Override
	protected MPackageInfo getPackage(CElementInfo info) {
		return getPackage(info.parent);
	}

	private final Map<String, MPackageInfo> packages = new HashMap<String, MPackageInfo>();

	private MPackageInfo getPackage(CClassInfoParent parent) {

		return parent.accept(new Visitor<MPackageInfo>() {

			@Override
			public MPackageInfo onBean(CClassInfo bean) {
				return getPackage(bean.parent());
			}

			@Override
			public MPackageInfo onPackage(JPackage pkg) {
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
			public MPackageInfo onElement(CElementInfo element) {
				return getPackage(element.parent);
			}
		});

	}

	@Override
	protected String getLocalName(CClassInfo info) {
		return info.shortName;
	}

	@Override
	protected String getLocalName(CEnumLeafInfo info) {
		return info.shortName;
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
}
