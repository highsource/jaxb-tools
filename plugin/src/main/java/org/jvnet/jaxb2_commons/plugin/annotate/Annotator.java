package org.jvnet.jaxb2_commons.plugin.annotate;

import java.util.Collection;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationFieldVisitor;
import org.jvnet.annox.model.annotation.field.XAnnotationField;

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
