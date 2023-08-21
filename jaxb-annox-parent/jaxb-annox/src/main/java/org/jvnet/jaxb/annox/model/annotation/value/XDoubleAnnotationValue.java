package org.jvnet.jaxb.annox.model.annotation.value;

public class XDoubleAnnotationValue extends XStaticAnnotationValue<Double> {

	public XDoubleAnnotationValue(double value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
