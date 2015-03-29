package org.jvnet.jaxb2_commons.xml.bind.model;

import org.jvnet.jaxb2_commons.xml.bind.model.origin.MOriginated;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MPropertyInfoOrigin;

public interface MPropertyInfo<T, C extends T> extends MOriginated<MPropertyInfoOrigin> {

	public MClassInfo<T, C> getClassInfo();

	public String getPrivateName();

	public String getPublicName();

	public boolean isCollection();

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor);
}
