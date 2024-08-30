package org.jvnet.jaxb.annox.model.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.parser.XGenericFieldParser;

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
		Assertions.assertEquals(42, result.integerField());
		Assertions.assertArrayEquals(new int[] { 37, 73 }, result.integerFields());
	}
}
