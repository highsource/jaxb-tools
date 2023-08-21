package org.jvnet.jaxb.annox.model.annotation.value;

public class XShortAnnotationValue extends XStaticAnnotationValue<Short> {

	public XShortAnnotationValue(short value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
