package org.jvnet.hyperjaxb3.ejb.strategy.annotate;

import org.jvnet.annox.model.XAnnotationFieldVisitor;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JCodeModel;

public class Annotator extends
		org.jvnet.jaxb2_commons.plugin.annotate.Annotator {

	@Override
	protected XAnnotationFieldVisitor<?> createAnnotationFieldVisitor(
			JCodeModel codeModel, JAnnotationUse annotationUse) {
		return new AnnotatingVisitor(codeModel, annotationUse);
	}

}
