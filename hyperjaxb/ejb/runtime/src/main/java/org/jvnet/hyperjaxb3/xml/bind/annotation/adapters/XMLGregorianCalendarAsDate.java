package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsDate extends
		AbstractXMLGregorianCalendarAdapter {

	@Override
	public Date createDate(XMLGregorianCalendar calendar) {
		return new java.sql.Date(calendar.normalize().toGregorianCalendar()
				.getTimeInMillis());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setYear(date.getYear() + 1900);
		calendar.setMonth(date.getMonth() + 1);
		calendar.setDay(date.getDate());
	}
}
