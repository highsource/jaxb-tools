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

import java.util.Collection;

import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.XAnnotationFieldVisitor;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

public class Annotator {

	public static final Annotator INSTANCE = new Annotator();

	public void annotate(JCodeModel codeModel, JAnnotatable annotatable,
			Collection<XAnnotation<?>> xannotations) {
		for (final XAnnotation<?> xannotation : xannotations) {
			if (xannotation != null) {
				annotate(codeModel, annotatable, xannotation);
			}
		}
	}

	public void annotate(JCodeModel codeModel, JAnnotatable annotatable,
			XAnnotation<?> xannotation) {
		final JClass annotationClass = codeModel.ref(xannotation
				.getAnnotationClass());
		JAnnotationUse annotationUse = null;
		for (JAnnotationUse annotation : annotatable.annotations()) {
			if (annotationClass.equals(annotation.getAnnotationClass())) {
				annotationUse = annotation;
			}
		}
		if (annotationUse == null) {
			annotationUse = annotatable.annotate(annotationClass);
		}
		final XAnnotationFieldVisitor<?> visitor = createAnnotationFieldVisitor(
				codeModel, annotationUse);
		for (XAnnotationField<?> field : xannotation.getFieldsList()) {
			field.accept(visitor);
		}
	}

	protected XAnnotationFieldVisitor<?> createAnnotationFieldVisitor(
			JCodeModel codeModel, final JAnnotationUse annotationUse) {
		final XAnnotationFieldVisitor<?> visitor = new AnnotatingVisitor(
				codeModel, annotationUse);
		return visitor;
	}

}
