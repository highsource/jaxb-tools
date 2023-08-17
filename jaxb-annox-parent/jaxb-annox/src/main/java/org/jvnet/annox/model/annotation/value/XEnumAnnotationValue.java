package org.jvnet.annox.model.annotation.value;

public class XEnumAnnotationValue<E extends Enum<E>> extends
		XStaticAnnotationValue<E> {

	public XEnumAnnotationValue(E value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
