package org.jvnet.jaxb.annox.demos.guide.tests;

import java.lang.reflect.AnnotatedElement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.demos.guide.MyAnnotation;
import org.jvnet.jaxb.annox.demos.guide.MyClass;
import org.jvnet.jaxb.annox.reflect.AnnotatedElementFactory;
import org.jvnet.jaxb.annox.reflect.DualAnnotatedElementFactory;

public class MyClassTest {

    @Test
	public void testNormal() throws Exception {
		final AnnotatedElement myClass = MyClass.class;
		final AnnotatedElement myField = MyClass.class.getDeclaredField("myField");
		final MyAnnotation myClassAnnotation = myClass.getAnnotation(MyAnnotation.class);
		final MyAnnotation myFieldAnnotation = myField.getAnnotation(MyAnnotation.class);

		Assertions.assertEquals("My class", myClassAnnotation.printName());
        Assertions.assertEquals("My field", myFieldAnnotation.printName());
	}

    @Test
	public void testAnnox() throws Exception {

		final AnnotatedElementFactory aef = new DualAnnotatedElementFactory();

		final AnnotatedElement myClass = aef.getAnnotatedElement(MyClass.class);
		final AnnotatedElement myField = aef.getAnnotatedElement(MyClass.class.getDeclaredField("myField"));
		final MyAnnotation myClassAnnotation = myClass.getAnnotation(MyAnnotation.class);
		final MyAnnotation myFieldAnnotation = myField.getAnnotation(MyAnnotation.class);

        Assertions.assertEquals("My annotated class", myClassAnnotation.printName());
        Assertions.assertEquals("My annotated field", myFieldAnnotation.printName());
	}

}
