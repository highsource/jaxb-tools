package org.jvnet.hyperjaxb3.xsd.util;

import javax.xml.namespace.QName;

public class XMLSchemaConstrants {

	public static final String XML_SCHEMA_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema";

	public static QName xsd(String localName) {
		return new QName(XML_SCHEMA_NAMESPACE_URI, localName);
	}

	public static QName xsd(String localName, String prefix) {
		return new QName(XML_SCHEMA_NAMESPACE_URI, localName, prefix);
	}
	
	public static final QName DATE_TIME_QNAME = xsd("dateTime");
	public static final QName DATE_QNAME = xsd("date");
	public static final QName TIME_QNAME = xsd("time");
	
	public static final QName G_YEAR_MONTH_QNAME = xsd("gYearMonth");
	public static final QName G_YEAR_QNAME = xsd("gYear");
	public static final QName G_MONTH_QNAME = xsd("gMonth");
	public static final QName G_MONTH_DAY_QNAME = xsd("gMonthDay");
	public static final QName G_DAY_QNAME = xsd("gDay");
	

}
