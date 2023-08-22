package org.jvnet.hyperjaxb3.xsom.tests;

import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;

import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.XSOMParser;

public class SimpleTypesAnalyzerTest extends TestCase {

	public static final String NS = "urn:org.jvnet.hyperjaxb3.xsom.tests";

	public static final String SCHEMA_RESOURCE = SimpleTypesAnalyzerTest.class
			.getPackage().getName().replace('.', '/')
			+ "/" + "SimpleTypesAnalyze.xsd";

	public XSSchemaSet parse(String resource) throws Exception {
		final XSOMParser parser = new XSOMParser();
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

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		schemaSet = parse(SCHEMA_RESOURCE);
	}

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

		Assert.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(minLength));
		Assert.assertEquals(null, SimpleTypeAnalyzer.getMaxLength(minLength));
		Assert.assertEquals(null, SimpleTypeAnalyzer.getLength(minLength));

		Assert.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(maxLength));
		Assert.assertEquals(Long.valueOf(10), SimpleTypeAnalyzer
				.getMaxLength(maxLength));
		Assert.assertEquals(null, SimpleTypeAnalyzer.getLength(maxLength));

		Assert.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getMinLength(length));
		Assert.assertEquals(Long.valueOf(10), SimpleTypeAnalyzer
				.getMaxLength(length));
		Assert.assertEquals(Long.valueOf(8), SimpleTypeAnalyzer
				.getLength(length));
		
		Assert.assertEquals(Long.valueOf(5), SimpleTypeAnalyzer
				.getTotalDigits(digits));
		Assert.assertEquals(Long.valueOf(2), SimpleTypeAnalyzer
				.getFractionDigits(digits));
		
		Assert.assertEquals(Long.valueOf(3), SimpleTypeAnalyzer
				.getTotalDigits(totalDigits));
		Assert.assertEquals(null, SimpleTypeAnalyzer
				.getFractionDigits(totalDigits));

		Assert.assertEquals(null, SimpleTypeAnalyzer
				.getTotalDigits(fractionDigits));
		Assert.assertEquals(Long.valueOf(2), SimpleTypeAnalyzer
				.getFractionDigits(fractionDigits));
	}
}
