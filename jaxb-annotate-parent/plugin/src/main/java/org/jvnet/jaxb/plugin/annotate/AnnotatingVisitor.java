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
package org.jvnet.jaxb.plugin.annotate;

import org.jvnet.jaxb.annox.model.XAnnotationFieldVisitor;
import org.jvnet.jaxb.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;

import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JCodeModel;

public class AnnotatingVisitor implements
		XAnnotationFieldVisitor<JAnnotationUse> {

	private final JCodeModel codeModel;
	private final JAnnotationUse annotationUse;

	public AnnotatingVisitor(JCodeModel codeModel, JAnnotationUse annotationUse) {
		this.codeModel = codeModel;
		this.annotationUse = annotationUse;
	}

	public JAnnotationUse visitSingleAnnotationField(
			XSingleAnnotationField<?> field) {
		final XAnnotationValue<?> annotationValue = field.getAnnotationValue();
		annotationValue.accept(new AnnotatingSingleValueVisitor(this.codeModel,
				field.getName(), this.annotationUse));
		return this.annotationUse;
	}

	public JAnnotationUse visitArrayAnnotationField(
			XArrayAnnotationField<?> field) {

		String fieldName = field.getName();
		final JAnnotationArrayMember annotationArrayMember = this.annotationUse
				.paramArray(fieldName);

		for (final XAnnotationValue<?> annotationValue : field
				.getAnnotationValues()) {
			annotationValue.accept(new AnnotatingArrayValueVisitor(
					this.codeModel, annotationArrayMember));
		}
		return this.annotationUse;
	}

}
