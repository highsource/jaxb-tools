package org.jvnet.jaxb2_commons.xml.bind.model;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public interface MPropertyInfo extends MOriginated<MPropertyInfoOrigin> {
	
	public MClassInfo getClassInfo();

	public String getPrivateName();

	public boolean isCollection();

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<V> visitor);
}
