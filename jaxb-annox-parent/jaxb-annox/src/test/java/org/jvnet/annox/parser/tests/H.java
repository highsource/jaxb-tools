/**
 * 
 */
package org.jvnet.annox.parser.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface H {
	long longField() default 1;

	int intField() default 2;

	short shortField()  default 3;

	char charField()  default 'c';

	byte byteField() default 5;

	double doubleField() default 6d;

	float floatField() default 7f;

	boolean booleanField() default true;

	String stringField() default "nine";

	E enumField() default E.ONE;

	Class<?> classField() default String.class;

	G annotationField() default @G();
}