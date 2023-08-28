/**
 *
 */
package org.jvnet.jaxb.annox.parser.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface I {

	long[] longArrayField() default { 1, 2 };

	int[] intArrayField() default { 3, 4 };

	short[] shortArrayField() default { 5, 6 };

	char[] charArrayField() default { 'e', 'n' };

	byte[] byteArrayField() default { 10, 11 };

	double[] doubleArrayField() default { 12d, 13d };

	float[] floatArrayField() default { 14f, 15f };

	boolean[] booleanArrayField() default { true, false };

	String[] stringArrayField() default { "18", "19" };

	E[] enumArrayField() default { E.ONE, E.TWO };

	Class<?>[] classArrayField() default { Object.class, String.class };

	I.C[] annotationArrayField() default { @C, @C };

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface C {
	}
}
