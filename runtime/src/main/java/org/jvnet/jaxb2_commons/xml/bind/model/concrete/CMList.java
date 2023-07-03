package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MList;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMList<T, C extends T> implements MList<T, C> {

	private final MTypeInfo<T, C> itemTypeInfo;
	private final T targetType;
	private final MCustomizations customizations = new CMCustomizations();
	private final QName typeName;

	public CMList(T targetType, MTypeInfo<T, C> itemTypeInfo, QName typeName) {
		Validate.notNull(targetType);
		Validate.notNull(itemTypeInfo);
		this.targetType = targetType;
		this.itemTypeInfo = itemTypeInfo;
		this.typeName = typeName;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public T getTargetType() {
		return targetType;
	}
	
	@Override
	public QName getTypeName() {
		return typeName;
	}
	
	@Override
	public boolean isSimpleType() {
		return true;
	}

	public MTypeInfo<T, C> getItemTypeInfo() {
		return itemTypeInfo;
	}

	@Override
	public String toString() {
		return MessageFormat.format("List [{0}]", getItemTypeInfo());
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitList(this);
	}
}
