package org.jvnet.annox.model.annotation.value;

public class XClassAnnotationValue<I> extends XStaticAnnotationValue<Class<I>> {

	public XClassAnnotationValue(Class<I> value) {
		super(value);
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
