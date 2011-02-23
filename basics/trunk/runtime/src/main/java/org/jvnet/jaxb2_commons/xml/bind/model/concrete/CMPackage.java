package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

public class CMPackage implements MPackageInfo {

	private final String packageName;

	public CMPackage(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	public String getPackagedName(String localName) {
		if (StringUtils.isEmpty(packageName)) {
			return localName;
		} else {
			return packageName + "." + localName;
		}
	}

}
