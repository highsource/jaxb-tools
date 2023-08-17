package org.jvnet.annox.model.annotation.value;

public class XIntAnnotationValue extends XStaticAnnotationValue<Integer> {

	public XIntAnnotationValue(int value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
