/**
 * Copyright © 2005-2015, Alexey Valikov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
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
