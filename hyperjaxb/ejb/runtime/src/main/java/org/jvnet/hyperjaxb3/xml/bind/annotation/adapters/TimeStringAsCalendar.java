package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TimeStringAsCalendar extends XmlAdapter<String, Calendar> {

	private static final DatatypeFactory datatypeFactory;

	static {
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new Error(e);
		}
	}

	@Override
	public String marshal(Calendar time) throws Exception {
		if (time == null) {
			return null;
		} else {
			return DatatypeConverter.printTime(time);
		}
	}

	@Override
	public Calendar unmarshal(String time) throws Exception {
		if (time == null) {
			return null;
		} else {
			final XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory
					.newXMLGregorianCalendar(time);
			final TimeZone timeZone;
			timeZone = xmlGregorianCalendar.getTimeZone(0);
			final Calendar calendar = xmlGregorianCalendar.toGregorianCalendar(
					timeZone, Locale.getDefault(), null);
			return calendar;
		}
	}
}
