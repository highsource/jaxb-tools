package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MList;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMList implements MList {

	private final MTypeInfo itemTypeInfo;

	public CMList(MTypeInfo itemTypeInfo) {
		Validate.notNull(itemTypeInfo);
		this.itemTypeInfo = itemTypeInfo;
	}

	@Override
	public MTypeInfo getItemTypeInfo() {
		return itemTypeInfo;
	}

	@Override
	public String toString() {
		return MessageFormat.format("List [{0}]", getItemTypeInfo());
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitList(this);
	}
}
