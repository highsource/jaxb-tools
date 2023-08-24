package org.jvnet.jaxb.annox.model.annotation.value;

public class XByteAnnotationValue extends XStaticAnnotationValue<Byte> {

	public XByteAnnotationValue(byte value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
