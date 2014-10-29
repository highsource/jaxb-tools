package org.jvnet.jaxb2_commons.plugin.simplify.tests01;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * 
 * @author
 */
public class DateAdapter {

  private static final DateTimeFormatter s_parser = ISODateTimeFormat.dateTimeParser().withZoneUTC();
  private static final DateTimeFormatter s_formatter = ISODateTimeFormat.dateTime().withZoneUTC();

  public static Date parseDateTime(String v) {

    try {
      return s_parser.parseDateTime(v).toDate();
    }
    catch (Exception e) {
      return null;
    }

  }

  /** {@inheritDoc} */

  public static String printDateTime(Date v) {
    return new DateTime(v).toString(s_formatter);
  }

}
