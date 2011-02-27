package org.jvnet.jaxb2_commons.xml.bind.model;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MBuiltinLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MBuiltinLeafInfo extends MTypeInfo,
		MOriginated<MBuiltinLeafInfoOrigin> {

	public QName getTypeName();

}
