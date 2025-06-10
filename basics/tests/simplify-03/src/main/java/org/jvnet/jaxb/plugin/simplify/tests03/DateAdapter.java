package org.jvnet.jaxb.plugin.simplify.tests03;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd['T'HH:mm:ss[.SSS][XXX]]")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			.toFormatter()
			.withZone(ZoneId.of("UTC"));

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME
			.withZone(ZoneId.of("UTC"));

	@Override
	public String marshal(Date value) throws Exception {
		return FORMATTER.format(value.toInstant());
	}

	@Override
	public Date unmarshal(String text) throws Exception {
		return Date.from(Instant.from(PARSER.parse(text)));
	}

}
