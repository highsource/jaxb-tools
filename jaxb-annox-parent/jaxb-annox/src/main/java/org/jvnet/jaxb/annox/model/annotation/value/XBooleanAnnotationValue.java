package org.jvnet.jaxb.annox.model.annotation.value;

public class XBooleanAnnotationValue extends XStaticAnnotationValue<Boolean> {

	public XBooleanAnnotationValue(boolean value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}

}
