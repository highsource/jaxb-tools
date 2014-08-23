/**
 * 
 */
package org.jvnet.jaxb2_commons.plugin.annotate.test.annox.annotations;

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

	Class<?>[] anotherClassArrayField() default { String.class, Boolean.class };

	B.C[] annotationArrayField();

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface C {
	}
}