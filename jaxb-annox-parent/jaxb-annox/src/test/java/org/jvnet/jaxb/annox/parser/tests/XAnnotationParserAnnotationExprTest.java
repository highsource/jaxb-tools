package org.jvnet.jaxb.annox.parser.tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XAnnotationParserAnnotationExprTest extends TestCase {

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

	public String getAnnotationString(final String resourceName)
			throws Exception {
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(resourceName);
			final String text = IOUtils.toString(is, "UTF-8");
			return text;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public void check(String javaResourceName, String xmlResourceName,
			Class<?> clazz, Class<? extends Annotation> annotationClazz)
			throws Exception {
		final String annotationExpr = getAnnotationString(javaResourceName);
		final Element element = getElement(xmlResourceName);
		final Annotation annotation = clazz.getAnnotation(annotationClazz);
		final XAnnotationParser parser = new XAnnotationParser();
		final XAnnotation<?> one = parser.parse(annotation);
		final XAnnotation<?> two = parser.parse(annotationExpr);
		final XAnnotation<?> three = parser.parse(element);
		System.out.println(one.toString());
		System.out.println(two.toString());
		System.out.println(three.toString());
		Assert.assertEquals("Annotations should be identical.", one, two);
		Assert.assertEquals("Annotations should be identical.", two, three);
		Assert.assertEquals("Annotations should be identical.",
				one.getResult(), two.getResult());
		Assert.assertEquals("Annotations should be identical.",
				two.getResult(), three.getResult());
		Assert.assertEquals("Annotations should be identical.", annotation,
				one.getResult());
		Assert.assertEquals("Annotations should be identical.", annotation,
				two.getResult());
		Assert.assertEquals("Annotations should be identical.", annotation,
				three.getResult());
	}

	public void testOne() throws Exception {
		check("one.txt", "one.xml", One.class, A.class);
	}

	public void testTwo() throws Exception {
		check("two.txt", "two.xml", Two.class, A.class);
	}

	public void testThree() throws Exception {
		check("three.txt", "three.xml", Three.class, D.class);
	}

	public void testFive() throws Exception {
		check("five.txt", "five.xml", Five.class, F.class);
	}

	public void testSix() throws Exception {
		check("six.txt", "six.xml", Six.class, G.class);
	}

	public void testSeven() throws Exception {
		check("seven.txt", "seven.xml", Seven.class, J.class);
	}

	public void testEight() throws Exception {
		check("eight.txt", "eight.xml", Eight.class, K.class);
	}

	public void testNine() throws Exception {
		check("nine.txt", "nine.xml", Nine.class, L.class);
	}

	public void testTen() throws Exception {
		check("ten.txt", "ten.xml", Ten.class, M.class);
	}

	public void testEleven() throws Exception {
		check("eleven.txt", "eleven.xml", Eleven.class, H.class);
	}

	public void testTwelve() throws Exception {
		check("twelve.txt", "twelve.xml", Twelve.class, B.class);
	}
}
