package org.jvnet.hyperjaxb3.xsom.tests;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;

import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.XSOMParser;

import javax.xml.parsers.SAXParserFactory;

public class SimpleTypesAnalyzerTest {

	public static final String NS = "urn:org.jvnet.hyperjaxb3.xsom.tests";

	public static final String SCHEMA_RESOURCE = SimpleTypesAnalyzerTest.class
			.getPackage().getName().replace('.', '/')
			+ "/" + "SimpleTypesAnalyze.xsd";

	public XSSchemaSet parse(String resource) throws Exception {
		final XSOMParser parser = new XSOMParser(SAXParserFactory.newInstance());
		parser.setErrorHandler(null);
		parser.setEntityResolver(null);

		final URL resourceUrl = getClass().getClassLoader().getResource(
				resource);
		// parser.parseSchema(
		//
		// new File("myschema.xsd"));
		// parser.parseSchema( new File("XHTML.xsd"));
		parser.parse(resourceUrl);
		XSSchemaSet sset = parser.getResult();
		return sset;
	}

	private XSSchemaSet schemaSet;

	public XSSchemaSet getSchemaSet() {
		return schemaSet;
	}

	@BeforeEach
	protected void setUp() throws Exception {
		schemaSet = parse(SCHEMA_RESOURCE);
	}

    @Test
	public void testLength() throws Exception {

		// XSSimpleType simpleType = schema.getSimpleType("length");
		// simpleType.toString();

		// for (Entry<String, XSType> entry : schemaSet.getSimpleType(arg0,
		// arg1)getTypes().entrySet()) {
		// System.out.println(entry.getKey());
		// }

		final XSSimpleType minLength = schemaSet.getSimpleType(NS, "minLength");
		final XSSimpleType maxLength = schemaSet.getSimpleType(NS, "maxLength");
		final XSSimpleType length = schemaSet.getSimpleType(NS, "length");

		final XSSimpleType digits = schemaSet.getSimpleType(NS, "digits");
		final XSSimpleType totalDigits = schemaSet.getSimpleType(NS, "totalDigits");
		final XSSimpleType fractionDigits = schemaSet.getSimpleType(NS, "fractionDigits");

		Assertions.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(minLength));
		Assertions.assertEquals(null, SimpleTypeAnalyzer.getMaxLength(minLength));
		Assertions.assertEquals(null, SimpleTypeAnalyzer.getLength(minLength));

		Assertions.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(maxLength));
		Assertions.assertEquals(Long.valueOf(10), SimpleTypeAnalyzer
				.getMaxLength(maxLength));
		Assertions.assertEquals(null, SimpleTypeAnalyzer.getLength(maxLength));

		Assertions.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(length));
		Assertions.assertEquals(Long.valueOf(10), SimpleTypeAnalyzer
				.getMaxLength(length));
		Assertions.assertEquals(Long.valueOf(8), SimpleTypeAnalyzer
				.getLength(length));

		Assertions.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getTotalDigits(digits));
		Assertions.assertEquals(Long.valueOf(2), SimpleTypeAnalyzer
				.getFractionDigits(digits));

		Assertions.assertEquals(Long.valueOf(3), SimpleTypeAnalyzer
				.getTotalDigits(totalDigits));
		Assertions.assertEquals(null, SimpleTypeAnalyzer
				.getFractionDigits(totalDigits));

		Assertions.assertEquals(null, SimpleTypeAnalyzer
				.getTotalDigits(fractionDigits));
		Assertions.assertEquals(Long.valueOf(2), SimpleTypeAnalyzer
				.getFractionDigits(fractionDigits));
	}
}
