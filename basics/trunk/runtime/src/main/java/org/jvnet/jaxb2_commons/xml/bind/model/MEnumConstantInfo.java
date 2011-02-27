package org.jvnet.jaxb2_commons.xml.bind.model;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumConstantInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;

public interface MEnumConstantInfo extends MOriginated<MEnumConstantInfoOrigin>{

	public String getLexicalValue();
}
