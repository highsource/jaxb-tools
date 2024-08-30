package org.jvnet.jaxb.annox.parser.tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XStringAnnotationValue;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XAnnotationParserTest {

	public Element getElement(final String resourceName) throws Exception {
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(resourceName);
			final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			final DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			final Document document = documentBuilder.parse(is);
			return document.getDocumentElement();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				// ignore
			}
		}

	}

    @Test
	public void testA() throws Exception {

		XAnnotationParser parser = new XAnnotationParser();

		final Element element = getElement("one.xml");

		final XAnnotation<?> one = parser.parse(element);
		System.out.println(one.toString());
		final A zeroResult = One.class.getAnnotation(A.class);
		final int zeroHashCode = zeroResult.hashCode();
		final String zeroToString = zeroResult.toString();
		final XAnnotation<?> two = parser.parse(zeroResult);
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");
		final Annotation oneResult = one.getResult();
		final int oneHashCode = oneResult.hashCode();
		final String oneToString = oneResult.toString();
		final Annotation twoResult = two.getResult();
		final int twoHashCode = twoResult.hashCode();
		final String twoToString = twoResult.toString();
		final XAnnotation<?> three = parser.parse(oneResult);
		System.out.println(three.toString());
		final XAnnotation<?> four = parser.parse(twoResult);
		System.out.println(four.toString());
		Assertions.assertEquals(three, four, "Annotations should be identical.");
		// Following line failes on JDK11+ since toString representation changed
		// Assertions.assertEquals("Annotation toStrings must have the same length",
		// zeroToString.length(), oneToString.length());
		Assertions.assertEquals(oneToString.length(), twoToString.length(),
            "Annotation toStrings must have the same length");
		Assertions.assertEquals(zeroHashCode, oneHashCode,
            "Annotation hashCodes should be identical.");
		Assertions.assertEquals(oneHashCode, twoHashCode,
            "Annotation hashCodes should be identical.");
		Assertions.assertEquals(zeroResult, oneResult, "Annotation should be equal.");
		Assertions.assertEquals(oneResult, twoResult, "Annotation should be equal.");
	}

    @Test
	public void testD() throws Exception {

		XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final Element element = getElement("d.xml");

		final XAnnotation<?> one = parser.parse(element);
		System.out.println(one.toString());
		final XAnnotation<?> two = parser.parse(Three.class
				.getAnnotation(D.class));
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");
		final Annotation oneResult = one.getResult();
		final Annotation twoResult = two.getResult();
		final XAnnotation<?> three = parser.parse(oneResult);
		System.out.println(three.toString());
		final XAnnotation<?> four = parser.parse(twoResult);
		System.out.println(four.toString());
		Assertions.assertEquals(three, four, "Annotations should be identical.");
	}

    @Test
	public void testEquals() throws Exception {

		final Annotation one = One.class.getAnnotation(A.class);
		final Annotation two = Two.class.getAnnotation(A.class);
		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");
	}

    @Test
	public void testF() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Five.class
				.getAnnotation(F.class));

		final XAnnotation<F> two = new XAnnotation<F>(F.class,
				new XSingleAnnotationField<String>("eman", String.class,
						new XStringAnnotationValue("tset")));

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

    @Test
	public void testG() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Six.class
				.getAnnotation(G.class));

		final XAnnotation<G> two = new XAnnotation<G>(G.class);

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

    @Test
	public void testField() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final Element element = getElement("field.xml");

		final XAnnotation<?> zero = parser.parse(element);
		Assertions.assertEquals(
				zero.toString(),
				"@org.hibernate.search.annotations.Field(index=YES, store=NO)",
            "Annotations should be identical.");

	}

    @Test
	public void testFieldBridge() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final Element element = getElement("fieldBridge.xml");

		final XAnnotation<?> zero = parser.parse(element);

		System.out.println(zero.toString());
		Assertions.assertEquals(
				zero.toString(),
				"@org.hibernate.search.annotations.FieldBridge(impl=java.lang.String1[][][][], params=[])",
            "Annotations should be identical.");

	}

    @Test
	public void testH() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final Element element = getElement("h.xml");

		final XAnnotation<?> zero = parser.parse(element);

		final XAnnotation<?> one = parser.parse(Eleven.class
				.getAnnotation(H.class));

		final XAnnotation<H> two = new XAnnotation<H>(H.class);

		System.out.println(zero.toString());
		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(zero, one, "Annotations should be identical.");
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

// @Test
//	public void testI() throws Exception {
//
//		final XAnnotation<D> d = new XAnnotation<D>(D.class);
//		@SuppressWarnings("unchecked")
//		final XAnnotation<D>[] ds = new XAnnotation[] { d };
//		XGenericFieldParser.generic().construct("d", d, d.getClass());
//		XGenericFieldParser.generic().construct("d", ds, ds.getClass());
//	}

    @Test
	public void testJ() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Seven.class
				.getAnnotation(J.class));

		final Element element = getElement("j.xml");

		final XAnnotation<?> two = parser.parse(element);

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

    @Test
	public void testK() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Eight.class
				.getAnnotation(K.class));

		final Element element = getElement("k.xml");

		final XAnnotation<?> two = parser.parse(element);

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

    @Test
	public void testL() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Nine.class
				.getAnnotation(L.class));

		final Element element = getElement("l.xml");

		final XAnnotation<?> two = parser.parse(element);

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");

	}

    @Test
	public void testM() throws Exception {

		final XAnnotationParser parser = XAnnotationParser.INSTANCE;

		final XAnnotation<?> one = parser.parse(Ten.class
				.getAnnotation(M.class));

		final Element element = getElement("m.xml");

		final XAnnotation<?> two = parser.parse(element);

		System.out.println(one.toString());
		System.out.println(two.toString());
		Assertions.assertEquals(one, two, "Annotations should be identical.");
	}
}
