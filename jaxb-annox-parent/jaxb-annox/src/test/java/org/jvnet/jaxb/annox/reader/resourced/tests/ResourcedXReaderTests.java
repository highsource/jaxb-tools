package org.jvnet.jaxb.annox.reader.resourced.tests;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XClass;
import org.jvnet.jaxb.annox.model.XField;
import org.jvnet.jaxb.annox.model.XMethod;
import org.jvnet.jaxb.annox.reader.XReader;
import org.jvnet.jaxb.annox.reader.resourced.ResourcedXReader;


public class ResourcedXReaderTests {

	protected XReader xreader;

	@BeforeEach
	public void setUp() {
		this.xreader = new ResourcedXReader();
	}

    @Test
	public void testOne() throws Exception {

		final XClass xone = xreader.getXClass(One.class);
		Assertions.assertNotNull(xone);
		Assertions.assertTrue(xone.isAnnotationPresent(Alpha.class));
		{
			Alpha alpha = xone.getAnnotation(Alpha.class);
			Assertions.assertEquals(2, alpha.longField());
			Assertions.assertEquals(3, alpha.intField());
			Assertions.assertEquals(4, alpha.shortField());
			Assertions.assertEquals('g', alpha.charField());
			Assertions.assertEquals(6, alpha.byteField());
			Assertions.assertEquals(6.0, alpha.doubleField(), 0.001);
			Assertions.assertEquals(7, alpha.floatField(), 0.001);
			Assertions.assertEquals(true, alpha.booleanField());
			Assertions.assertEquals("nine", alpha.stringField());
			Assertions.assertEquals(Epsilon.TEN, alpha.enumField());
			Assertions.assertEquals(Twelve.class, alpha.classField());
			Assertions.assertSame(xone, xreader.getXClass(One.class));
		}
		// Check field
		final XField xfieldA = xreader.getXField(One.class.getField("fieldA"));
		Alpha ann_fieldA = xfieldA.getAnnotation(Alpha.class);
		Assertions.assertEquals(16, ann_fieldA.intField());

		// Check methods
		final XMethod xgetFieldA = xreader.getXMethod(One.class
				.getMethod("getFieldA"));
		Alpha ann_getFieldA = xgetFieldA.getAnnotation(Alpha.class);
		Assertions.assertEquals(32, ann_getFieldA.shortField());

		final XMethod xsetFieldA = xreader.getXMethod(One.class.getMethod(
				"setFieldA", int.class));
		Alpha ann_setFieldA = xsetFieldA.getAnnotation(Alpha.class);
		Assertions.assertEquals('Z', ann_setFieldA.charField());

		final XMethod xsetFieldB1 = xreader.getXMethod(One.class
				.getMethod("setFieldB"));
		Alpha ann_setFieldB1 = xsetFieldB1.getAnnotation(Alpha.class);
		Assertions.assertEquals(64, ann_setFieldB1.byteField());

		final XMethod xsetFieldB2 = xreader.getXMethod(One.class.getMethod(
				"setFieldB", String.class));
		Alpha ann_setFieldB2 = xsetFieldB2.getAnnotation(Alpha.class);
		Assertions.assertEquals(65, ann_setFieldB2.doubleField(), 0.001);

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", String.class, int.class));
			final Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assertions.assertEquals(128, alpha.floatField(), 0.001);
			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assertions.assertEquals(2, pa.length);
			Assertions.assertEquals(0, pa[0].length);
			Assertions.assertEquals(1, pa[1].length);
			Assertions.assertEquals("int", ((Alpha) pa[1][0]).stringField());

		}

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", String.class, int.class, byte.class));
			Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assertions.assertEquals(129, alpha.floatField(), 0.001);
			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assertions.assertEquals(3, pa.length);
			Assertions.assertEquals(1, pa[0].length);
			Assertions.assertEquals(0, pa[1].length);
			Assertions.assertEquals(1, pa[2].length);
			Assertions.assertEquals("java.lang.String", ((Alpha) pa[0][0])
					.stringField());
			Assertions.assertEquals("byte", ((Alpha) pa[2][0]).stringField());
		}

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", int.class, byte.class));
			Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assertions.assertEquals(130, alpha.floatField(), 0.001);

			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assertions.assertEquals(2, pa.length);
			Assertions.assertEquals(1, pa[0].length);
			Assertions.assertEquals(1, pa[1].length);
			Assertions.assertEquals("int", ((Alpha) pa[0][0]).stringField());
			Assertions.assertEquals("byte", ((Alpha) pa[1][0]).stringField());
		}
	}

}
