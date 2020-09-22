package org.jvnet.jaxb2_commons.plugin.simplify.tests01;

import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * 
 * @author
 */
public class DateAdapter extends XmlAdapter<String, Date> {

	private static final DateTimeFormatter PARSER = ISODateTimeFormat
			.dateTimeParser().withZoneUTC();
	private static final DateTimeFormatter FORMATTER = ISODateTimeFormat
			.dateTime().withZoneUTC();

	@Override
	public String marshal(Date value) throws Exception {
		return new DateTime(value).toString(FORMATTER);
	}

	@Override
	public Date unmarshal(String text) throws Exception {
		return PARSER.parseDateTime(text).toDate();
	}

}
