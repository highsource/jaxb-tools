package org.jvnet.annox.reader.resourced.tests;

import java.lang.annotation.Annotation;

import org.junit.Assert;
import junit.framework.TestCase;

import org.jvnet.annox.model.XClass;
import org.jvnet.annox.model.XField;
import org.jvnet.annox.model.XMethod;
import org.jvnet.annox.reader.XReader;
import org.jvnet.annox.reader.resourced.ResourcedXReader;

public class ResourcedXReaderTests extends TestCase {

	protected XReader xreader;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.xreader = new ResourcedXReader();
	}

	public void testOne() throws Exception {

		final XClass xone = xreader.getXClass(One.class);
		Assert.assertNotNull(xone);
		Assert.assertTrue(xone.isAnnotationPresent(Alpha.class));
		{
			Alpha alpha = xone.getAnnotation(Alpha.class);
			Assert.assertEquals(2, alpha.longField());
			Assert.assertEquals(3, alpha.intField());
			Assert.assertEquals(4, alpha.shortField());
			Assert.assertEquals('g', alpha.charField());
			Assert.assertEquals(6, alpha.byteField());
			Assert.assertEquals(6.0, alpha.doubleField(), 0.001);
			Assert.assertEquals(7, alpha.floatField(), 0.001);
			Assert.assertEquals(true, alpha.booleanField());
			Assert.assertEquals("nine", alpha.stringField());
			Assert.assertEquals(Epsilon.TEN, alpha.enumField());
			Assert.assertEquals(Twelve.class, alpha.classField());
			Assert.assertSame(xone, xreader.getXClass(One.class));
		}
		// Check field
		final XField xfieldA = xreader.getXField(One.class.getField("fieldA"));
		Alpha ann_fieldA = xfieldA.getAnnotation(Alpha.class);
		Assert.assertEquals(16, ann_fieldA.intField());

		// Check methods
		final XMethod xgetFieldA = xreader.getXMethod(One.class
				.getMethod("getFieldA"));
		Alpha ann_getFieldA = xgetFieldA.getAnnotation(Alpha.class);
		Assert.assertEquals(32, ann_getFieldA.shortField());

		final XMethod xsetFieldA = xreader.getXMethod(One.class.getMethod(
				"setFieldA", int.class));
		Alpha ann_setFieldA = xsetFieldA.getAnnotation(Alpha.class);
		Assert.assertEquals('Z', ann_setFieldA.charField());

		final XMethod xsetFieldB1 = xreader.getXMethod(One.class
				.getMethod("setFieldB"));
		Alpha ann_setFieldB1 = xsetFieldB1.getAnnotation(Alpha.class);
		Assert.assertEquals(64, ann_setFieldB1.byteField());

		final XMethod xsetFieldB2 = xreader.getXMethod(One.class.getMethod(
				"setFieldB", String.class));
		Alpha ann_setFieldB2 = xsetFieldB2.getAnnotation(Alpha.class);
		Assert.assertEquals(65, ann_setFieldB2.doubleField(), 0.001);

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", String.class, int.class));
			final Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assert.assertEquals(128, alpha.floatField(), 0.001);
			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assert.assertEquals(2, pa.length);
			Assert.assertEquals(0, pa[0].length);
			Assert.assertEquals(1, pa[1].length);
			Assert.assertEquals("int", ((Alpha) pa[1][0]).stringField());

		}

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", String.class, int.class, byte.class));
			Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assert.assertEquals(129, alpha.floatField(), 0.001);
			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assert.assertEquals(3, pa.length);
			Assert.assertEquals(1, pa[0].length);
			Assert.assertEquals(0, pa[1].length);
			Assert.assertEquals(1, pa[2].length);
			Assert.assertEquals("java.lang.String", ((Alpha) pa[0][0])
					.stringField());
			Assert.assertEquals("byte", ((Alpha) pa[2][0]).stringField());
		}

		{
			final XMethod xmethod = xreader.getXMethod(One.class.getMethod(
					"setFieldC", int.class, byte.class));
			Alpha alpha = xmethod.getAnnotation(Alpha.class);
			Assert.assertEquals(130, alpha.floatField(), 0.001);

			final Annotation[][] pa = xmethod.getParameterAnnotations();
			Assert.assertEquals(2, pa.length);
			Assert.assertEquals(1, pa[0].length);
			Assert.assertEquals(1, pa[1].length);
			Assert.assertEquals("int", ((Alpha) pa[0][0]).stringField());
			Assert.assertEquals("byte", ((Alpha) pa[1][0]).stringField());
		}
	}

}
