package org.jvnet.jaxb.annox.model.annotation.value;

public abstract class AbstractBasicXAnnotationValueVisitor<R> implements
		XAnnotationValueVisitor<R> {

	public abstract R visitDefault(XAnnotationValue<?> value);

	@Override
	public R visit(XXAnnotationAnnotationValue<?> value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XBooleanAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XByteAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XCharAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XDoubleAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XFloatAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XIntAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XLongAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XShortAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XStringAnnotationValue value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XEnumAnnotationValue<?> value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XEnumByNameAnnotationValue<?> value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XClassAnnotationValue<?> value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XClassByNameAnnotationValue<?> value) {
		return visitDefault(value);
	}

	@Override
	public R visit(XArrayClassAnnotationValue<?, ?> value) {
		return visitDefault(value);
	}

}
