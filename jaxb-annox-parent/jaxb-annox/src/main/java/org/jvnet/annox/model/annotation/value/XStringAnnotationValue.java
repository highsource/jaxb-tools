package org.jvnet.annox.model.annotation.value;

public class XStringAnnotationValue extends XStaticAnnotationValue<String> {

	public XStringAnnotationValue(String value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
