package edu.hawaii.halealohacli.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;

/**
 * Used for obtaining the amount of energy used on a particular day.
 * @author Terrence Chida
 *
 */
public class DailyEnergy {

  /*
  private WattDepotClient client;
  */

  /**
   * Constructor for the DailyEnergy class.
   * @param client The client passed in from the main method.
   */
  /*
  public DailyEnergy(WattDepotClient client) {
    this.client = client;
  }
  */

  /**
   * Runs the program.
   * 
   * @param args
   *          Command line arguments.
   * @throws Exception
   *           Any Exception.
   */
  // @Override
  public static void main(String[] args) throws Exception {
    if (args.length == 2) {
      List<String> ydm = new ArrayList<String>();
      Double energy;
      String url = "http://server.wattdepot.org:8190/wattdepot/";
      String tower = args[0];
      String date = args[1];
      if (VerifyDate.isValidDate(date)) {
        StringTokenizer st = new StringTokenizer(date, "-");
        while (st.hasMoreTokens()) {
          ydm.add(st.nextToken());
        }
        Integer year = Integer.parseInt(ydm.get(0));
        Integer month = Integer.parseInt(ydm.get(1));
        Integer day = Integer.parseInt(ydm.get(2));
        // Create a client.
        WattDepotClient client = new WattDepotClient(url);
        // Check to make sure a connection can be made.
        // If no connection, then exit right now.
        if (client.isHealthy()) {
          XMLGregorianCalendar now = client.getLatestSensorData(tower)
              .getTimestamp();
          int thisMonth = now.getMonth();
          int today = now.getDay();
          XMLGregorianCalendar now2 = client.getLatestSensorData(tower)
              .getTimestamp();
          XMLGregorianCalendar start = DailyEnergy.setDay(now, year, month, day,
              0, 0, 0, 0);
          XMLGregorianCalendar end = DailyEnergy.setDay(now2, year, month, day,
              23, 59, 59, 999);
          // Check to see if input date is before today's date.
          if (end.getMonth() <= thisMonth && end.getDay() < today) {
            energy = client.getEnergyConsumed(tower, start, end, 0);
            System.out.println(start);
            System.out.println(end);
            System.out.print(tower + "'s energy consumption for " + date
                + " was: " + (Math.round(energy) / 1000) + " kWh.");
          }
          else {
            System.out.println("Date must be before today.");
          }
        }
        else {
          System.out.format("Could not connect to: %s%n", url);
          return;
        }
      }
    } // end if one arg provided.

  }

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
  public static XMLGregorianCalendar setDay(XMLGregorianCalendar today, int year,
      int month, int day, int hour, int min, int sec, int millisec) {
    XMLGregorianCalendar d = today;
    d.setYear(year);
    d.setMonth(month);
    d.setDay(day);
    d.setTime(hour, min, sec, millisec);
    return d;
  } // end day()

}