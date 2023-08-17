package org.jvnet.annox.model.annotation.value;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.Validate;
import org.jvnet.annox.model.XAnnotation;

public class XXAnnotationAnnotationValue<A extends Annotation> extends
		XDynamicAnnotationValue<A> {

	private final A annotation;
	private final XAnnotation<A> xannotation;

	public XXAnnotationAnnotationValue(XAnnotation<A> xannotation) {
		this.xannotation = Validate.notNull(xannotation);
		this.annotation = null;
	}

	public XXAnnotationAnnotationValue(A annotation, XAnnotation<A> xannotation) {
		this.xannotation = Validate.notNull(xannotation);
		this.annotation = null;
	}

	public XAnnotation<A> getXAnnotation() {
		return this.xannotation;
	}

	@Override
	public A getValue() {
		if (this.annotation == null) {
			return this.xannotation.getResult();
		} else {
			return this.annotation;
		}
	}

	@Override
	protected Object getInternalValue() {
		return this.xannotation;
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}

}
