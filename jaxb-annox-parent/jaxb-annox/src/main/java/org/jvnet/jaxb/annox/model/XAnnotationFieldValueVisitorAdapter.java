//package org.jvnet.jaxb.annox.model;
//
//import org.apache.commons.lang3.Validate;
//import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
//import org.jvnet.jaxb.annox.model.annotation.field.XArrayAnnotationField;
//import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
//import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValueVisitor;
//import org.jvnet.jaxb.annox.model.annotation.value.XArrayClassAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XBooleanAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XByteAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XCharAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XClassAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XClassByNameAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XDoubleAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XEnumAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XEnumByNameAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XFloatAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XIntAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XLongAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XShortAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XStringAnnotationValue;
//import org.jvnet.jaxb.annox.model.annotation.value.XXAnnotationAnnotationValue;
//
//public class XAnnotationFieldValueVisitorAdapter<T> implements
//		XAnnotationFieldVisitor<Void> {
//
//	private final XAnnotationFieldValueVisitor<T> fieldValueVisitor;
//
//	public XAnnotationFieldValueVisitorAdapter(
//			XAnnotationFieldValueVisitor<T> fieldValueVisitor) {
//		Validate.notNull(fieldValueVisitor);
//		this.fieldValueVisitor = fieldValueVisitor;
//	};
//
//	@Override
//	public Void visitSingleAnnotationField(XSingleAnnotationField<?> field) {
//		final XAnnotationValue<?> value = field.getAnnotationValue();
//		value.accept(new XSingleAnnotationFieldAnnotationValueVisitor(field));
//		return null;
//	}
//
//	@Override
//	public Void visitArrayAnnotationField(XArrayAnnotationField<?> field) {
//		for (XAnnotationValue<?> value : field.getAnnotationValues()) {
//			value.accept(new XArrayAnnotationFieldAnnotationValueVisitor(field));
//		}
//		return null;
//	}
//
//	private class XSingleAnnotationFieldAnnotationValueVisitor implements
//			XAnnotationValueVisitor<T> {
//		private final XSingleAnnotationField<?> field;
//
//		public XSingleAnnotationFieldAnnotationValueVisitor(
//				XSingleAnnotationField<?> field) {
//			super();
//			this.field = field;
//		}
//
//		@Override
//		public T visit(XAnnotationAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XXAnnotationAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XBooleanAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XByteAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XCharAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XDoubleAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XFloatAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XIntAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XLongAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XShortAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XStringAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XEnumAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XEnumByNameAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XClassAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XClassByNameAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XArrayClassAnnotationValue<?, ?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//	}
//
//	private class XArrayAnnotationFieldAnnotationValueVisitor implements
//			XAnnotationValueVisitor<T> {
//		private final XArrayAnnotationField<?> field;
//
//		public XArrayAnnotationFieldAnnotationValueVisitor(
//				XArrayAnnotationField<?> field) {
//			super();
//			this.field = field;
//		}
//
//		@Override
//		public T visit(XAnnotationAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XXAnnotationAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XBooleanAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XByteAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XCharAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XDoubleAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XFloatAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XIntAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XLongAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XShortAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XStringAnnotationValue value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XEnumAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XEnumByNameAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XClassAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XClassByNameAnnotationValue<?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//		@Override
//		public T visit(XArrayClassAnnotationValue<?, ?> value) {
//			return XAnnotationFieldValueVisitorAdapter.this.fieldValueVisitor
//					.visit(field, value);
//		}
//
//	}
//
//}
