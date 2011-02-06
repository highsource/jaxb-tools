package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

public interface MElementRefsPropertyInfo extends MPropertyInfo, MMixable,
		MWrappable, MWildcard, MElementTypeInfos {

	@Override
	public List<MElementTypeInfo> getElementTypeInfos();

}
