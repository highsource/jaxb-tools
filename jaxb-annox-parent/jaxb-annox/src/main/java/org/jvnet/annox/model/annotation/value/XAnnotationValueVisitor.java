package org.jvnet.annox.model.annotation.value;

public interface XAnnotationValueVisitor<R> {

	public R visit(XXAnnotationAnnotationValue<?> value);

	public R visit(XBooleanAnnotationValue value);

	public R visit(XByteAnnotationValue value);

	public R visit(XCharAnnotationValue value);

	public R visit(XDoubleAnnotationValue value);

	public R visit(XFloatAnnotationValue value);

	public R visit(XIntAnnotationValue value);

	public R visit(XLongAnnotationValue value);

	public R visit(XShortAnnotationValue value);

	public R visit(XStringAnnotationValue value);

	public R visit(XEnumAnnotationValue<?> value);

	public R visit(XEnumByNameAnnotationValue<?> value);

	public R visit(XClassAnnotationValue<?> value);

	public R visit(XClassByNameAnnotationValue<?> value);

	public R visit(XArrayClassAnnotationValue<?, ?> value);
}
