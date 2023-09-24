package org.jvnet.jaxb.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.MCustomizations;
import org.jvnet.jaxb.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb.xml.bind.model.MWildcardTypeInfo;
import org.jvnet.jaxb.xml.bind.model.origin.MWildcardTypeInfoOrigin;

public class CMWildcardTypeInfo<T, C extends T> implements
		MWildcardTypeInfo<T, C> {

	private final T targetType;
	private final MWildcardTypeInfoOrigin origin;
	private final MCustomizations customizations = new CMCustomizations();

	public CMWildcardTypeInfo(MWildcardTypeInfoOrigin origin, T targetType) {
		Validate.notNull(origin);
		this.origin = origin;
		this.targetType = targetType;
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public T getTargetType() {
		return targetType;
	}

	@Override
	public QName getTypeName() {
		return null;
	}

	@Override
	public boolean isSimpleType() {
		return false;
	}

	public MWildcardTypeInfoOrigin getOrigin() {
		return origin;
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitWildcardTypeInfo(this);
	}

}
