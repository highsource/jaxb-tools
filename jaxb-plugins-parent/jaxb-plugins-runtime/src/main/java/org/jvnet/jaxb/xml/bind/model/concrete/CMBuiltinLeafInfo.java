package org.jvnet.jaxb.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.MBuiltinLeafInfo;
import org.jvnet.jaxb.xml.bind.model.MCustomizations;
import org.jvnet.jaxb.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb.xml.bind.model.origin.MBuiltinLeafInfoOrigin;

public class CMBuiltinLeafInfo<T, C extends T> implements MBuiltinLeafInfo<T, C> {

	private final MBuiltinLeafInfoOrigin origin;
	private final T targetType;
	private final QName typeName;
	private final MCustomizations customizations = new CMCustomizations();

	public CMBuiltinLeafInfo(MBuiltinLeafInfoOrigin origin, T targetType,
			QName typeName) {
		Validate.notNull(origin);
		Validate.notNull(targetType);
		Validate.notNull(typeName);
		this.origin = origin;
		this.targetType = targetType;
		this.typeName = typeName;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public T getTargetType() {
		return targetType;
	}

	public MBuiltinLeafInfoOrigin getOrigin() {
		return origin;
	}

	public QName getTypeName() {
		return typeName;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}

	public String toString() {
		return "BuiltinLeafInfo [" + getTypeName() + "]";
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitBuiltinLeafInfo(this);
	}
}
