package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsDateTime extends
		AbstractXMLGregorianCalendarAdapter {

	public Date createDate(XMLGregorianCalendar calendar) {
		final java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar
				.normalize().toGregorianCalendar().getTimeInMillis());
		return timestamp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setYear(date.getYear() + 1900);
		calendar.setMonth(date.getMonth() + 1);
		calendar.setDay(date.getDate());
		calendar.setHour(date.getHours());
		calendar.setMinute(date.getMinutes());
		calendar.setSecond(date.getSeconds());
		calendar.setMillisecond((int) (date.getTime() % 1000));
	}
}
