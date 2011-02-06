package org.jvnet.jaxb2_commons.xml.bind.model;

import java.util.List;

public interface MElementsPropertyInfo extends MPropertyInfo, MWrappable,
		MElementTypeInfos {

	@Override
	public List<MElementTypeInfo> getElementTypeInfos();
}
