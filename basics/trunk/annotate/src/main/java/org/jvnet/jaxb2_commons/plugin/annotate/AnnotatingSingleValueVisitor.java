package org.jvnet.jaxb2_commons.plugin.annotate;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.annox.model.annotation.value.XAnnotationValueVisitor;
import org.jvnet.annox.model.annotation.value.XArrayClassAnnotationValue;
import org.jvnet.annox.model.annotation.value.XBooleanAnnotationValue;
import org.jvnet.annox.model.annotation.value.XByteAnnotationValue;
import org.jvnet.annox.model.annotation.value.XCharAnnotationValue;
import org.jvnet.annox.model.annotation.value.XClassAnnotationValue;
import org.jvnet.annox.model.annotation.value.XClassByNameAnnotationValue;
import org.jvnet.annox.model.annotation.value.XDoubleAnnotationValue;
import org.jvnet.annox.model.annotation.value.XEnumAnnotationValue;
import org.jvnet.annox.model.annotation.value.XEnumByNameAnnotationValue;
import org.jvnet.annox.model.annotation.value.XFloatAnnotationValue;
import org.jvnet.annox.model.annotation.value.XIntAnnotationValue;
import org.jvnet.annox.model.annotation.value.XLongAnnotationValue;
import org.jvnet.annox.model.annotation.value.XShortAnnotationValue;
import org.jvnet.annox.model.annotation.value.XStringAnnotationValue;
import org.jvnet.annox.model.annotation.value.XXAnnotationAnnotationValue;
import org.jvnet.jaxb2_commons.util.CodeModelUtils;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class AnnotatingSingleValueVisitor implements
		XAnnotationValueVisitor<JAnnotationUse> {

	private final JCodeModel codeModel;
	private final String name;
	private final JAnnotationUse annotationUse;

	public AnnotatingSingleValueVisitor(JCodeModel codeModel, String name,
			JAnnotationUse annotationUse) {
		this.codeModel = codeModel;
		this.name = name;
		this.annotationUse = annotationUse;
	}

//	public JAnnotationUse visit(XAnnotationAnnotationValue<?> value) {
//		final XAnnotation<?> xannotation = value.getXAnnotation();
//
//		final JAnnotationUse annotationUse = this.annotationUse
//				.annotationParam(this.name, xannotation.getAnnotationClass());
//
//		for (final XAnnotationField<?> field : xannotation.getFieldsList()) {
//			field.accept(new AnnotatingVisitor(this.codeModel, annotationUse));
//		}
//		return annotationUse;
//	}

	public JAnnotationUse visit(XXAnnotationAnnotationValue<?> value) {
		final XAnnotation<?> xannotation = value.getXAnnotation();

		// TODO The call to getAnnotationClass is illegal here
		final JAnnotationUse annotationUse = this.annotationUse
				.annotationParam(this.name, xannotation.getAnnotationClass());

		for (final XAnnotationField<?> field : xannotation.getFieldsList()) {
			field.accept(new AnnotatingVisitor(this.codeModel, annotationUse));
		}
		return annotationUse;
	}

	public JAnnotationUse visit(XBooleanAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XByteAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XCharAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XDoubleAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XFloatAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XIntAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XLongAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XShortAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XStringAnnotationValue value) {
		return annotationUse.param(this.name, value.getValue());
	}

	public JAnnotationUse visit(XEnumAnnotationValue<?> value) {
		final Enum<?> e = value.getValue();
		return annotationUse.param(this.name, e);
	}

	public JAnnotationUse visit(XEnumByNameAnnotationValue<?> value) {
		final JClass type = (JClass) CodeModelUtils.ref(this.codeModel,
				value.getEnumClassName());
		return annotationUse.param(this.name, type.staticRef(value.getName()));
	}

	public JAnnotationUse visit(XClassAnnotationValue<?> value) {
		final JType type = this.codeModel.ref(value.getValue());
		return annotationUse.param(this.name, type);
	}

	public JAnnotationUse visit(XClassByNameAnnotationValue<?> value) {
		JType type = CodeModelUtils.ref(this.codeModel, value.getClassName());
		return annotationUse.param(this.name, type);
	}

	public JAnnotationUse visit(XArrayClassAnnotationValue<?, ?> value) {
		JType type = CodeModelUtils.ref(this.codeModel,
				value.getItemClassName());
		for (int index = 0; index < value.getDimension(); index++) {
			type = type.array();
		}
		return annotationUse.param(this.name, type);
	}

}
