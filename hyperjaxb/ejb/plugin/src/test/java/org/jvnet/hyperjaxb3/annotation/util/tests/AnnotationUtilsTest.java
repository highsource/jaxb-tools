package org.jvnet.hyperjaxb3.annotation.util.tests;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

public class AnnotationUtilsTest {

    @Test
	public void testA() throws Exception {

		final Collection<XAnnotation<?>> a = new LinkedList<XAnnotation<?>>();
		a.add(new XAnnotation<Override>(Override.class));
		a.add(new XAnnotation<Override>(Override.class));
		XAnnotationField<Annotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

    @Test
	public void testB() throws Exception {

		final Collection<XAnnotation<?>> a = new LinkedList<XAnnotation<?>>();
		XAnnotationField<Annotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

}
