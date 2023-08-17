package org.jvnet.annox.parser.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface B {
	long[] longArrayField();

	int[] intArrayField();

	short[] shortArrayField();

	char[] charArrayField();

	byte[] byteArrayField();

	double[] doubleArrayField();

	float[] floatArrayField();

	boolean[] booleanArrayField();

	String[] stringArrayField();

	E[] enumArrayField();

	Class<?>[] classArrayField();

	B.C[] annotationArrayField();

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface C {
	}
}