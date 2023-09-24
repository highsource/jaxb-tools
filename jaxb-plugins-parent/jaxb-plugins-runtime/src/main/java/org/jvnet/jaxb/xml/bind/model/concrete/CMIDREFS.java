package org.jvnet.jaxb.xml.bind.model.concrete;

import java.text.MessageFormat;

import org.jvnet.jaxb.xml.bind.model.MIDREFS;
import org.jvnet.jaxb.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb.xmlschema.XmlSchemaConstants;

public class CMIDREFS<T, C extends T> extends CMList<T, C> implements MIDREFS<T, C> {

	public CMIDREFS(T targetType, MTypeInfo<T, C> itemTypeInfo) {
		super(targetType, itemTypeInfo, XmlSchemaConstants.IDREFS);
	}

	@Override
	public String toString() {
		return MessageFormat.format("IDREFS [{0}]", getItemTypeInfo());
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitIDREFS(this);
	}
}
