package org.jvnet.jaxb.xml.bind.model;

import org.jvnet.jaxb.xml.bind.model.origin.MOriginated;
import org.jvnet.jaxb.xml.bind.model.origin.MPackageInfoOrigin;

public interface MPackageInfo extends MOriginated<MPackageInfoOrigin>,
		MContainer {

	public String getPackageName();

	public String getPackagedName(String localName);
}
