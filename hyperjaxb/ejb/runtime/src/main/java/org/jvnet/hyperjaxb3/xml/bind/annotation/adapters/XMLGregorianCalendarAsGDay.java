package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGDay extends XMLGregorianCalendarAsDate {

	@SuppressWarnings("deprecation")
	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setDay(date.getDate());
	}
	
}
