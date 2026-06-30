package org.jvnet.hyperjaxb3.adapters.tests;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jakarta.xml.bind.JAXBElement;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.DurationAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.QNameAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.TimeStringAsCalendar;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public class XmlAdapterUtilsTest {

    @Test
	public void testQNameXmlAdapter() throws Exception {

		final String alpha = "{urn:test}test";
		final QName omega = new QName("urn:test", "test");

		Assertions.assertEquals(alpha, XmlAdapterUtils
				.unmarshall(QNameAsString.class, omega), "Conversion failed.");
		Assertions.assertEquals(omega, XmlAdapterUtils
				.marshall(QNameAsString.class, alpha), "Conversion failed.");
	}

    @Test
	public void testDuration() throws Exception {

		final String alpha = "P1Y2M3DT10H30M12.3S";
		final Duration omega = DatatypeFactory.newInstance().newDuration(alpha);

		Assertions.assertEquals(alpha, XmlAdapterUtils
				.unmarshall(DurationAsString.class, omega), "Conversion failed.");
		Assertions.assertEquals(omega, XmlAdapterUtils
				.marshall(DurationAsString.class, alpha), "Conversion failed.");
	}

    @Test
	public void testXMLGregorianCalendarXmlAdapter() throws Exception {

		final XMLGregorianCalendar alpha = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T11:00:00.012+04:00");

		final XMLGregorianCalendar omega = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T09:00:00.012+02:00");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDateTime.class,
				XmlAdapterUtils.unmarshall(
						XMLGregorianCalendarAsDateTime.class, alpha));
		// Assertions.assertEquals("Conversion failed.", alpha.normalize(),
		// omega.normalize());
		// Assertions.assertEquals("Conversion failed.", alpha.normalize(),
		// beta.normalize());
		// Assertions.assertEquals("Conversion failed.", beta.normalize(),
		// omega.normalize());
		Assertions.assertEquals(XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(beta), "Conversion failed.");
		Assertions.assertEquals(XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(omega), "Conversion failed.");
		Assertions.assertEquals(XMLGregorianCalendarUtils
				.getTimeInMillis(beta), XMLGregorianCalendarUtils
				.getTimeInMillis(omega), "Conversion failed.");
	}

    @Test
	public void testXMLGregorianCalendarAsDate() throws Exception {

		final java.sql.Date alpha = java.sql.Date.valueOf("2005-01-01");

		System.out.println("1)" + alpha.getTime());

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, alpha);

		System.out.println("2)" + beta.toGregorianCalendar().getTimeInMillis());
		System.out.println("2>" + beta);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDate.class, beta);
		System.out.println("3)" + gamma.getTime());
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, gamma);
		System.out.println("4)"
				+ delta.toGregorianCalendar().getTime().getTime());
		System.out.println("4>" + delta);
		Assertions.assertEquals(beta, delta, "Conversion failed.");
	}

    @Test
	public void testXMLGregorianCalendarAsDateInNegativeTimezone()
			throws Exception {

		TimeZone _default = TimeZone.getDefault();

		TimeZone.setDefault(TimeZone.getTimeZone("GMT-2"));

		final java.sql.Date alpha = java.sql.Date.valueOf("2005-01-01");

		System.out.println("1)" + alpha.getTime());

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, alpha);

		System.out.println("2)" + beta.toGregorianCalendar().getTimeInMillis());

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDate.class, beta);
		System.out.println("3)" + gamma.getTime());
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, gamma);
		System.out.println("4)"
				+ delta.toGregorianCalendar().getTime().getTime());
		Assertions.assertEquals(beta, delta, "Conversion failed.");

		TimeZone.setDefault(_default);
	}

    @Test
	public void testXMLGregorianCalendarAsTime() throws Exception {

		final java.sql.Time alpha = java.sql.Time.valueOf("10:12:14");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, alpha);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsTime.class, beta);
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, gamma);
		Assertions.assertEquals(beta, delta, "Conversion failed.");
	}

    @Test
	public void testTimeStringAsCalendarXmlAdapter() throws Exception {

		checkTimeStringAsCalendarXmlAdapter("10:20:30");
		checkTimeStringAsCalendarXmlAdapter("10:20:30Z");
		checkTimeStringAsCalendarXmlAdapter("12:13:14+01:00");
		checkTimeStringAsCalendarXmlAdapter("12:13:14+02:00");
		checkTimeStringAsCalendarXmlAdapter("12:13:14-03:00");
	}

	private void checkTimeStringAsCalendarXmlAdapter(final String alpha) {
		final Calendar beta = XmlAdapterUtils.unmarshall(
				TimeStringAsCalendar.class, alpha);
		final String gamma = XmlAdapterUtils.marshall(
				TimeStringAsCalendar.class, beta);
		final Calendar delta = XmlAdapterUtils.unmarshall(
				TimeStringAsCalendar.class, gamma);
		final String epsilon = XmlAdapterUtils.marshall(
				TimeStringAsCalendar.class, delta);
		// Assertions.assertEquals("Conversion failed.", alpha, gamma);
		Assertions.assertEquals(beta, delta, "Conversion failed.");
		Assertions.assertEquals(gamma, epsilon, "Conversion failed.");
	}

    @Test
	public void testXMLGregorianCalendarAsDateTimeXmlAdapter() throws Exception {

		checkXMLGregorianCalendarAsDateTimeXmlAdapter("2005-01-01T00:00:00.000+00:00");
		checkXMLGregorianCalendarAsDateTimeXmlAdapter("2005-01-01T09:00:00.012+02:00");
		checkXMLGregorianCalendarAsDateTimeXmlAdapter("2008-01-02T10:18:30+01:00");
		checkXMLGregorianCalendarAsDateTimeXmlAdapter("2008-01-02T10:19:30Z");
		checkXMLGregorianCalendarAsDateTimeXmlAdapter("2008-01-02T10:20:30");
	}

	private void checkXMLGregorianCalendarAsDateTimeXmlAdapter(final String text)
			throws DatatypeConfigurationException {

		final XMLGregorianCalendar alpha = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(text);
		System.out.println("T]" + alpha.getTimezone());
		long a = alpha.toGregorianCalendar().getTimeInMillis();
		System.out.println("1]" + a);
		final Date beta = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDateTime.class, alpha);
		long b = beta.getTime();
		System.out.println("2]" + b);
		final XMLGregorianCalendar gamma = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDateTime.class, beta);
		long c = gamma.toGregorianCalendar().getTimeInMillis();
		System.out.println("3]" + c);
		final Date delta = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDateTime.class, gamma);
		long d = delta.getTime();
		System.out.println("4]" + d);
		final XMLGregorianCalendar epsilon = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDateTime.class, delta);
		long e = epsilon.toGregorianCalendar().getTimeInMillis();
		System.out.println("5]" + e);
		// Assertions.assertEquals("Conversion failed.", alpha, gamma);
		Assertions.assertEquals(beta, delta, "Conversion failed.");
		Assertions.assertEquals(gamma, epsilon, "Conversion failed.");
		Assertions.assertEquals(a, b, "Conversion failed.");
		Assertions.assertEquals(b, c, "Conversion failed.");
		Assertions.assertEquals(c, d, "Conversion failed.");
		Assertions.assertEquals(d, e, "Conversion failed.");
	}

//	public void testXMLGregorianCalendarAsDateXmlAdapter() throws Exception {
//
//		checkXMLGregorianCalendarAsDateXmlAdapter("2008-01-02");
//		checkXMLGregorianCalendarAsDateXmlAdapter("2008-01-02Z");
//		checkXMLGregorianCalendarAsDateXmlAdapter("2005-01-01+00:00");
//		checkXMLGregorianCalendarAsDateXmlAdapter("2005-01-01+02:00");
//		checkXMLGregorianCalendarAsDateXmlAdapter("2008-01-02+01:00");
//	}
//
//	private void checkXMLGregorianCalendarAsDateXmlAdapter(final String text)
//			throws DatatypeConfigurationException {
//
//		final XMLGregorianCalendar alpha = DatatypeFactory.newInstance()
//				.newXMLGregorianCalendar(text);
//		System.out.println("T>" + alpha.getTimezone());
//
//		long a = alpha.toGregorianCalendar().getTimeInMillis();
//		System.out.println("1>" + a);
//		final Date beta = XmlAdapterUtils.unmarshall(
//				XMLGregorianCalendarAsDate.class, alpha);
//		long b = beta.getTime();
//		System.out.println("2>" + b);
//		final XMLGregorianCalendar gamma = XmlAdapterUtils.marshall(
//				XMLGregorianCalendarAsDate.class, beta);
//		long c = gamma.toGregorianCalendar().getTimeInMillis();
//		System.out.println("3>" + c);
//		final Date delta = XmlAdapterUtils.unmarshall(
//				XMLGregorianCalendarAsDate.class, gamma);
//		long d = delta.getTime();
//		System.out.println("4>" + d);
//		final XMLGregorianCalendar epsilon = XmlAdapterUtils.marshall(
//				XMLGregorianCalendarAsDate.class, delta);
//		long e = epsilon.toGregorianCalendar().getTimeInMillis();
//		System.out.println("5>" + e);
//		// Assertions.assertEquals("Conversion failed.", alpha, gamma);
//		// Assertions.assertEquals("Conversion failed.", beta, delta);
//		// Assertions.assertEquals("Conversion failed.", gamma, epsilon);
//		Assertions.assertEquals("Conversion failed.", a, b);
//		Assertions.assertEquals("Conversion failed.", b, c);
//		Assertions.assertEquals("Conversion failed.", c, d);
//		Assertions.assertEquals("Conversion failed.", d, e);
//	}

    @Test
	public void testGetConverter() throws Exception {
		final org.jvnet.hyperjaxb3.item.Converter<String, QName> converter = XmlAdapterUtils
				.getConverter(QNameAsString.class);
		Assertions.assertNotNull(converter);
		final QName qname = new QName("urn:test", "test");
		Assertions.assertEquals("{urn:test}test", converter.direct(qname));
		Assertions.assertEquals(qname, converter.inverse("{urn:test}test"));
	}

    @Test
	public void testAsConverter() throws Exception {
		final jakarta.xml.bind.annotation.adapters.XmlAdapter<QName, String> adapter = new QNameAsString();
		final org.jvnet.hyperjaxb3.item.Converter<String, QName> converter = XmlAdapterUtils
				.asConverter(adapter);
		Assertions.assertNotNull(converter);
		final QName qname = new QName("urn:test", "test");
		Assertions.assertEquals(qname, converter.inverse("{urn:test}test"));
	}

    @Test
	public void testIsJAXBElementWithValueNull() {
		final QName name = new QName("urn:test", "test");
		Assertions.assertFalse(XmlAdapterUtils.isJAXBElement(String.class, name,
				Object.class, null));
	}

    @Test
	public void testIsJAXBElementWithNonJAXBElementValue() {
		final QName name = new QName("urn:test", "test");
		Assertions.assertFalse(XmlAdapterUtils.isJAXBElement(String.class, name,
				Object.class, "not a jaxb element"));
	}

    @Test
	public void testIsJAXBElementWithMatchingElement() {
		final QName name = new QName("urn:test", "test");
		final JAXBElement<String> element = new JAXBElement<String>(name,
				String.class, null, "value");
		Assertions.assertTrue(XmlAdapterUtils.isJAXBElement(String.class, name,
				Object.class, element));
	}

    @Test
	public void testIsJAXBElementWithNonMatchingName() {
		final QName name = new QName("urn:test", "test");
		final QName otherName = new QName("urn:other", "other");
		final JAXBElement<String> element = new JAXBElement<String>(otherName,
				String.class, null, "value");
		Assertions.assertFalse(XmlAdapterUtils.isJAXBElement(String.class, name,
				Object.class, element));
	}

    @Test
	public void testIsJAXBElementWithNonMatchingType() {
		final QName name = new QName("urn:test", "test");
		final JAXBElement<String> element = new JAXBElement<String>(name,
				String.class, null, "value");
		Assertions.assertFalse(XmlAdapterUtils.isJAXBElement(Integer.class, name,
				Object.class, element));
	}

    @Test
	public void testIsJAXBElementWithAssignableType() {
		final QName name = new QName("urn:test", "test");
		final JAXBElement<String> element = new JAXBElement<String>(name,
				String.class, null, "value");
		Assertions.assertTrue(XmlAdapterUtils.isJAXBElement(Object.class, name,
				Object.class, element));
	}

    @Test
	public void testUnmarshallJAXBElementWithNull() {
		Assertions.assertNull(XmlAdapterUtils.unmarshallJAXBElement(
				QNameAsString.class, (JAXBElement<QName>) null));
	}

    @Test
	public void testUnmarshallJAXBElementWithValue() throws Exception {
		final QName qname = new QName("urn:test", "test");
		final JAXBElement<QName> element = new JAXBElement<QName>(
				new QName("urn:test", "element"), QName.class, null, qname);
		final String result = XmlAdapterUtils.unmarshallJAXBElement(
				QNameAsString.class, element);
		Assertions.assertEquals("{urn:test}test", result);
	}

    @Test
	public void testUnmarshallJAXBElementSimpleWithNull() {
		Assertions.assertNull(XmlAdapterUtils
				.unmarshallJAXBElement((JAXBElement<String>) null));
	}

    @Test
	public void testUnmarshallJAXBElementSimpleWithValue() {
		final QName name = new QName("urn:test", "element");
		final JAXBElement<String> element = new JAXBElement<String>(name,
				String.class, null, "hello");
		final String result = XmlAdapterUtils.unmarshallJAXBElement(element);
		Assertions.assertEquals("hello", result);
	}

    @Test
	public void testMarshallJAXBElementWithNull() {
		final QName name = new QName("urn:test", "element");
		Assertions.assertNull(XmlAdapterUtils.marshallJAXBElement(
				QNameAsString.class, QName.class, name, Object.class,
				(String) null));
	}

    @Test
	public void testMarshallJAXBElementWithValue() throws Exception {
		final QName name = new QName("urn:test", "element");
		final String value = "{urn:test}test";
		final JAXBElement<QName> result = XmlAdapterUtils.marshallJAXBElement(
				QNameAsString.class, QName.class, name, Object.class, value);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(name, result.getName());
		Assertions.assertEquals(new QName("urn:test", "test"), result.getValue());
	}

    @Test
	public void testMarshallJAXBElementSimpleWithNull() {
		final QName name = new QName("urn:test", "element");
		Assertions.assertNull(XmlAdapterUtils.marshallJAXBElement(String.class,
				name, Object.class, (String) null));
	}

    @Test
	public void testMarshallJAXBElementSimpleWithValue() {
		final QName name = new QName("urn:test", "element");
		final JAXBElement<String> result = XmlAdapterUtils.marshallJAXBElement(
				String.class, name, Object.class, "hello");
		Assertions.assertNotNull(result);
		Assertions.assertEquals(name, result.getName());
		Assertions.assertEquals("hello", result.getValue());
	}
}
