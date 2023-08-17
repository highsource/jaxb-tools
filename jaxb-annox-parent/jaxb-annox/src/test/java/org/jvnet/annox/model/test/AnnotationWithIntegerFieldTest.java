package org.jvnet.annox.model.test;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.annotation.field.XAnnotationField;
import org.jvnet.annox.parser.XGenericFieldParser;

public class AnnotationWithIntegerFieldTest {

	@Test
	public void test() {

		@SuppressWarnings("unchecked")
		final XAnnotationField<Integer> integerField = XGenericFieldParser.GENERIC
				.construct("integerField", Integer.valueOf(42), Integer.class);

		@SuppressWarnings("unchecked")
		final XAnnotationField<Integer[]> integerFields = XGenericFieldParser.GENERIC
				.construct("integerFields", new Integer[] { 37, 73 },
						Integer[].class);

		final XAnnotation<AnnotationWithIntegerField> annotation = new XAnnotation<AnnotationWithIntegerField>(
				AnnotationWithIntegerField.class, integerField, integerFields);

		AnnotationWithIntegerField result = annotation.getResult();
		Assert.assertEquals(42, result.integerField());
		Assert.assertArrayEquals(new int[] { 37, 73 }, result.integerFields());
	}
}
