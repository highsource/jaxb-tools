package org.jvnet.annox.demos.guide.tests;

import java.lang.reflect.AnnotatedElement;

import junit.framework.TestCase;

import org.jvnet.annox.demos.guide.Comment;
import org.jvnet.annox.demos.guide.DemoClass;
import org.jvnet.annox.reflect.AnnotatedElementFactory;
import org.jvnet.annox.reflect.DualAnnotatedElementFactory;
import org.jvnet.annox.reflect.ParameterizedAnnotatedElement;

public class DemoClassTest extends TestCase {

	public void testDemoClassAnnotations() throws Exception {

		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();

		final AnnotatedElement demoClass = aef
				.getAnnotatedElement(DemoClass.class);
		assertNotNull(demoClass.getAnnotation(Comment.class).value());

		final AnnotatedElement valueField = aef
				.getAnnotatedElement(DemoClass.class.getDeclaredField("value"));
		assertNotNull(valueField.getAnnotation(Comment.class).value());

		final AnnotatedElement defaultConstructor = aef
				.getAnnotatedElement(DemoClass.class.getConstructor());
		assertNotNull(defaultConstructor.getAnnotation(Comment.class).value());

		final ParameterizedAnnotatedElement secondConstructor = aef
				.getAnnotatedElement(DemoClass.class.getConstructor(int.class));
		assertNotNull(secondConstructor.getAnnotation(Comment.class).value());
		String t = ((Comment)secondConstructor.getParameterAnnotations()[0][0]).value();
		assertNotNull(t);
	}

}
