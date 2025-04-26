package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

public class XMLGregorianCalendarAsDateTimeTest {

    @Test
    public void testMarshal() throws Exception {
        Date d = new Date(987 + 1000 * 3600 * 24 * 3);
        XMLGregorianCalendarAsDateTime adapter = new XMLGregorianCalendarAsDateTime();
        XMLGregorianCalendar c = adapter.marshal(d);

        Assertions.assertEquals(987, c.getMillisecond());
    }


    @Test
    public void testMarshalPast() throws Exception {
        Date d = new Date(-987 - 1000 * 3600 * 24 * 3);
        XMLGregorianCalendarAsDateTime adapter = new XMLGregorianCalendarAsDateTime();
        XMLGregorianCalendar c = adapter.marshal(d);

        Assertions.assertEquals(13, c.getMillisecond());
    }
}
