package org.jvnet.jaxb.annox.parser.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XAnnotationParserAnnotationExprTest {

	public Element getElement(final String resourceName) throws Exception {
		try (InputStream is = getClass().getResourceAsStream(resourceName)){
			final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			final DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			final Document document = documentBuilder.parse(is);
			return document.getDocumentElement();
		}
	}

	public String getAnnotationString(final String resourceName)
			throws Exception {
		try (InputStream is = getClass().getResourceAsStream(resourceName)) {
			return readFromInputStream(is);
		}
	}

    private String readFromInputStream(InputStream inputStream)
        throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
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
		Assertions.assertEquals(one, two, "Annotations should be identical.");
		Assertions.assertEquals(two, three, "Annotations should be identical.");
		Assertions.assertEquals(one.getResult(), two.getResult(), "Annotations should be identical.");
		Assertions.assertEquals(two.getResult(), three.getResult(), "Annotations should be identical.");
		Assertions.assertEquals(annotation, one.getResult(), "Annotations should be identical.");
		Assertions.assertEquals(annotation, two.getResult(), "Annotations should be identical.");
		Assertions.assertEquals(annotation, three.getResult(), "Annotations should be identical.");
	}

    @Test
	public void testOne() throws Exception {
		check("one.txt", "one.xml", One.class, A.class);
	}

    @Test
	public void testTwo() throws Exception {
		check("two.txt", "two.xml", Two.class, A.class);
	}

    @Test
	public void testThree() throws Exception {
		check("three.txt", "three.xml", Three.class, D.class);
	}

    @Test
	public void testFive() throws Exception {
		check("five.txt", "five.xml", Five.class, F.class);
	}

    @Test
	public void testSix() throws Exception {
		check("six.txt", "six.xml", Six.class, G.class);
	}

    @Test
	public void testSeven() throws Exception {
		check("seven.txt", "seven.xml", Seven.class, J.class);
	}

    @Test
	public void testEight() throws Exception {
		check("eight.txt", "eight.xml", Eight.class, K.class);
	}

    @Test
	public void testNine() throws Exception {
		check("nine.txt", "nine.xml", Nine.class, L.class);
	}

    @Test
	public void testTen() throws Exception {
		check("ten.txt", "ten.xml", Ten.class, M.class);
	}

    @Test
	public void testEleven() throws Exception {
		check("eleven.txt", "eleven.xml", Eleven.class, H.class);
	}

    @Test
	public void testTwelve() throws Exception {
		check("twelve.txt", "twelve.xml", Twelve.class, B.class);
	}
}
