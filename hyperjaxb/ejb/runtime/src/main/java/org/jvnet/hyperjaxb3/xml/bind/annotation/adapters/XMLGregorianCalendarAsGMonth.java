package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGMonth extends XMLGregorianCalendarAsDate {

	@SuppressWarnings("deprecation")
	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setMonth(date.getMonth() + 1);
	}
}
