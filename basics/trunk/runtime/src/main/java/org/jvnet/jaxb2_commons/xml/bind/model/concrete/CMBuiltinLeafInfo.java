package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;

public class CMBuiltinLeafInfo implements MBuiltinLeafInfo {

	private final QName typeName;

	public CMBuiltinLeafInfo(QName typeName) {
		super();
		this.typeName = typeName;
	}

	@Override
	public QName getTypeName() {
		return typeName;
	}

	@Override
	public String toString() {
		return "BuiltinLeafInfo [" + getTypeName() + "]";
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitBuiltinLeafInfo(this);
	}
}
