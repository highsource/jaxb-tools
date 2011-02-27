package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MBuiltinLeafInfoOrigin;

public class CMBuiltinLeafInfo implements MBuiltinLeafInfo {

	private final MBuiltinLeafInfoOrigin origin;
	private final QName typeName;

	public CMBuiltinLeafInfo(MBuiltinLeafInfoOrigin origin, QName typeName) {
		Validate.notNull(origin);
		Validate.notNull(typeName);
		this.origin = origin;
		this.typeName = typeName;
	}

	public MBuiltinLeafInfoOrigin getOrigin() {
		return origin;
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
