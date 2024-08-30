package org.jvnet.jaxb.annox.demos.guide.tests;

import java.lang.reflect.AnnotatedElement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.demos.guide.Comment;
import org.jvnet.jaxb.annox.demos.guide.DemoClass;
import org.jvnet.jaxb.annox.reflect.AnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.DualAnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.ParameterizedAnnotatedElement;

public class DemoClassTest {

    @Test
	public void testDemoClassAnnotations() throws Exception {

		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();

		final AnnotatedElement demoClass = aef
				.getAnnotatedElement(DemoClass.class);
		Assertions.assertNotNull(demoClass.getAnnotation(Comment.class).value());

		final AnnotatedElement valueField = aef
				.getAnnotatedElement(DemoClass.class.getDeclaredField("value"));
        Assertions.assertNotNull(valueField.getAnnotation(Comment.class).value());

		final AnnotatedElement defaultConstructor = aef
				.getAnnotatedElement(DemoClass.class.getConstructor());
        Assertions.assertNotNull(defaultConstructor.getAnnotation(Comment.class).value());

		final ParameterizedAnnotatedElement secondConstructor = aef
				.getAnnotatedElement(DemoClass.class.getConstructor(int.class));
        Assertions.assertNotNull(secondConstructor.getAnnotation(Comment.class).value());
		String t = ((Comment) secondConstructor.getParameterAnnotations()[0][0]).value();
        Assertions.assertNotNull(t);
	}

}
