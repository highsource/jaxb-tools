/**
 * 
 */
package org.jvnet.jaxb.annox.reader.resourced.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Alpha {
	long longField() default 1;

	int intField() default 2;

	short shortField() default 3;

	char charField() default 'f';

	byte byteField() default 5;

	double doubleField() default 6.0;

	float floatField() default 7.0f;

	boolean booleanField() default true;

	String stringField() default "nine";

	Epsilon enumField() default Epsilon.TEN;

	Class<?> classField() default Eleven.class;

	// B annotationField();
}