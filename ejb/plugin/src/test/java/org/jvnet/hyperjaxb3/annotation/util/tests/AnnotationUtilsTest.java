package org.jvnet.hyperjaxb3.annotation.util.tests;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

public class AnnotationUtilsTest extends TestCase {

	public void testA() throws Exception {

		final Collection<XAnnotation<?>> a = new LinkedList<XAnnotation<?>>();
		a.add(new XAnnotation<Override>(Override.class));
		a.add(new XAnnotation<Override>(Override.class));
		XAnnotationField<Annotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

	public void testB() throws Exception {

		final Collection<XAnnotation<?>> a = new LinkedList<XAnnotation<?>>();
		XAnnotationField<Annotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

}