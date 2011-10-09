package org.jvnet.jaxb2_commons.xml.bind.model;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPackageInfoOrigin;

public interface MPackageInfo extends MOriginated<MPackageInfoOrigin> {

	public String getPackageName();

	public String getPackagedName(String localName);
}
