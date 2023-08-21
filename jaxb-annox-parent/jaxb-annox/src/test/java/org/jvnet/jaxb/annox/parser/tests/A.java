package org.jvnet.jaxb.annox.parser.tests;

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
	B annotationField();
}