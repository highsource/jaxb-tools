//package org.jvnet.annox.model;
//
//import org.jvnet.annox.model.annotation.field.XArrayAnnotationField;
//import org.jvnet.annox.model.annotation.field.XSingleAnnotationField;
//import org.jvnet.annox.model.annotation.value.XAnnotationAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XArrayClassAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XBooleanAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XByteAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XCharAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XClassAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XClassByNameAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XDoubleAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XEnumAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XEnumByNameAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XFloatAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XIntAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XLongAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XShortAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XStringAnnotationValue;
//import org.jvnet.annox.model.annotation.value.XXAnnotationAnnotationValue;
//
//public interface XAnnotationFieldValueVisitor<T> extends XAnnotationFieldVisitor<T> {
//
//	public T visit(XSingleAnnotationField<?> field,
//			XAnnotationAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XXAnnotationAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XBooleanAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XByteAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XCharAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XDoubleAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XFloatAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XIntAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XLongAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XShortAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field, XStringAnnotationValue value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XEnumAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XEnumByNameAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XClassAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XClassByNameAnnotationValue<?> value);
//
//	public T visit(XSingleAnnotationField<?> field,
//			XArrayClassAnnotationValue<?, ?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XAnnotationAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XXAnnotationAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field, XBooleanAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XByteAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XCharAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XDoubleAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XFloatAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XIntAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XLongAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XShortAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XStringAnnotationValue value);
//
//	public T visit(XArrayAnnotationField<?> field, XEnumAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XEnumByNameAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XClassAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XClassByNameAnnotationValue<?> value);
//
//	public T visit(XArrayAnnotationField<?> field,
//			XArrayClassAnnotationValue<?, ?> value);
//
//}
