package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MID;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xmlschema.XmlSchemaConstants;

public class CMID<T, C> implements MID<T, C> {

	private final MTypeInfo<T, C> valueTypeInfo;
	private final T targetType;
	private final MCustomizations customizations = new CMCustomizations();

	public CMID(T targetType, MTypeInfo<T, C> itemTypeInfo) {
		Validate.notNull(targetType);
		Validate.notNull(itemTypeInfo);
		this.targetType = targetType;
		this.valueTypeInfo = itemTypeInfo;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public T getTargetType() {
		return targetType;
	}

	public MTypeInfo<T, C> getValueTypeInfo() {
		return valueTypeInfo;
	}

	public QName getTypeName() {
		return XmlSchemaConstants.ID;
	}

	@Override
	public String toString() {
		return MessageFormat.format("ID [{0}]", getValueTypeInfo());
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitID(this);
	}
}
