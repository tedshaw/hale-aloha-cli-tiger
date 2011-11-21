package edu.hawaii.halealohacli.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.datatype.XMLGregorianCalendar;

import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Lists the towers and their corresponding energy consumption amounts in
 * ascending order.
 * 
 * @author Terrence Chida
 * 
 */
public class RankTowers {
  static String url = "http://server.wattdepot.org:8190/wattdepot/";

  /**
   * Runs the program.
   * 
   * @param args
   *          The command line arguments.
   * @throws Exception
   *           Any exception.
   */
  public static void main(String[] args) throws Exception {
    if (args.length == 2) {
      List<String> ydm = new ArrayList<String>();
      List<Data> list = new ArrayList<Data>();
      // Create a client.
      WattDepotClient client = new WattDepotClient(url);
      Double energy;
      String towerRegex = "^(Mokihana|Ilima|Lehua|Lokelani)$";
      String start = args[0];
      String end = args[1];
      boolean validStart = VerifyDate.isValidDate(start);
      boolean validEnd = VerifyDate.isValidDate(end);
      // Check to make sure a connection can be made.
      // If no connection, then exit right now.
      boolean clientHealthy = client.isHealthy();
      // Proceed only if the user input valid dates.
      if (validStart && validEnd && clientHealthy) {
        StringTokenizer st = new StringTokenizer(start, "-");
        StringTokenizer st2 = new StringTokenizer(end, "-");
        while (st.hasMoreTokens()) {
          ydm.add(st.nextToken());
        }
        while (st2.hasMoreTokens()) {
          ydm.add(st2.nextToken());
        }
        Integer startYear = Integer.parseInt(ydm.get(0));
        Integer startMonth = Integer.parseInt(ydm.get(1));
        Integer startDay = Integer.parseInt(ydm.get(2));
        Integer endYear = Integer.parseInt(ydm.get(3));
        Integer endMonth = Integer.parseInt(ydm.get(4));
        Integer endDay = Integer.parseInt(ydm.get(5));
        // Get the list of sources.
        List<Source> sources = client.getSources();
        Source testSource = sources.get(0);
        XMLGregorianCalendar now = client.getLatestSensorData(
            testSource.getName()).getTimestamp();
        XMLGregorianCalendar now2 = client.getLatestSensorData(
            testSource.getName()).getTimestamp();
        int thisMonth = now.getMonth();
        int today = now.getDay();
        XMLGregorianCalendar startTime = DailyEnergy.setDay(now, startYear,
            startMonth, startDay, 0, 0, 0, 0);
        XMLGregorianCalendar endTime = DailyEnergy.setDay(now2, endYear,
            endMonth, endDay, 23, 59, 59, 999);
        // Check to see if input date is before today's date.
        if (endTime.getMonth() <= thisMonth && endTime.getDay() < today) {
          for (Source source : sources) {
            String sourceName = source.getName();
            if (sourceName.matches(towerRegex)) {
              energy = client.getEnergyConsumed(sourceName, startTime, endTime,
                  0);
              list.add(new Data(sourceName, (int) Math.round(energy)));
            }
          } // End for each source
          Collections.sort(list, new SortByEnergy());
          System.out.format(
              "For the interval %s to %s, energy consumption by tower was:%n",
              start, end);
          for (Data data : list) {
            System.out.format("%8s  %s kWh%n", data.getSource(),
                data.getEnergy() / 1000);
          }
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
  } // End main()

} // End RankTowers
