package org.jvnet.hyperjaxb3.xml.datatype;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class XMLGregorianCalendarTest {

	protected Log logger = LogFactory.getLog(getClass());

	private DatatypeFactory datatypeFactory;
	{
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public DatatypeFactory getDatatypeFactory() {
		return datatypeFactory;
	}

    @Test
	public void testIt() {

		final XMLGregorianCalendar calendar = getDatatypeFactory()
				.newXMLGregorianCalendar();

		calendar.setDay(15);
		calendar.setTimezone(120);
		final String gDayString = calendar.toXMLFormat();
		logger.debug(gDayString);
	}

}
