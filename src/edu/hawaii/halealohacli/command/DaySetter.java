package edu.hawaii.halealohacli.command;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Class for setting a specified XMLGregorianCalendar date.
 * 
 * @author Terrence Chida
 * 
 */
public class DaySetter {
  /**
   * Creates an XMLGregorianCalendar date.
   * 
   * @param today
   *          Today's date.
   * @param year
   *          The year to set.
   * @param month
   *          The month to set.
   * @param day
   *          The day to set.
   * @param hour
   *          The hour to set.
   * @param min
   *          The minute to set.
   * @param sec
   *          The second to set.
   * @param millisec
   *          The millisecond to set.
   * @return The XMLGregorianCalendar date.
   */
  public static XMLGregorianCalendar setDay(XMLGregorianCalendar today,
      int year, int month, int day, int hour, int min, int sec, int millisec) {
    XMLGregorianCalendar d = today;
    d.setYear(year);
    d.setMonth(month);
    d.setDay(day);
    d.setTime(hour, min, sec, millisec);
    return d;
  } // end setDay()

} // end DaySetter.
