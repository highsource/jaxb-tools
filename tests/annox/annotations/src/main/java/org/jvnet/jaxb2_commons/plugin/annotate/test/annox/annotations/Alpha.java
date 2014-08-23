package org.jvnet.jaxb2_commons.plugin.annotate.test.annox.annotations;

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

	Digits enumField() default Digits.EIGHT;

	String stringField() default "nine";

	@SuppressWarnings("unchecked")
	Class classField() default String.class;

	// B annotationField();
}