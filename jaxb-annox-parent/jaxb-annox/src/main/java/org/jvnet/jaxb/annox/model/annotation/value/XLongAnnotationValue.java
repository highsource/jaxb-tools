package org.jvnet.jaxb.annox.model.annotation.value;

public class XLongAnnotationValue extends XStaticAnnotationValue<Long> {

	public XLongAnnotationValue(long value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
