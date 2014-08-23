/**
 * 
 */
package org.jvnet.jaxb2_commons.plugin.annotate.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface A {
	long longField();

	int intField();

	short shortField();

	char charField();

	byte byteField();

	double doubleField();

	float floatField();

	boolean booleanField();

	String stringField();

	E enumField();

	Class<?> classField();

	Class<?> anotherClassField() default String.class;

	B annotationField();
}