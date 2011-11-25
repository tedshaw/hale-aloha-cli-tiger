package edu.hawaii.halealohacli.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Lists the towers and their corresponding energy consumption amounts in ascending order.
 * 
 * @author Terrence Chida, Ardell Klemme
 * 
 */
public class RankTowers implements Command {

  // client for this command class, to be set upon call to constructor.
  WattDepotClient client;

  /**
   * Initializes the RankTowers class with a WattDepotClient that is passed from the Command
   * Processor class after it has been checked for a healthy connection.
   * 
   * @param client The client to be used for this command.
   */
  public RankTowers(WattDepotClient client) {
    this.client = client;
  }

  /**
   * Verifies whether or not command has the correct number of arguments.
   * 
   * @param command The string of command arguments.
   * @return true if correct number of arguments.
   */
  @Override
  public boolean isValid(String command) {
    String[] splitCommand = command.split(" ");
    if (splitCommand.length == 3) {
      return true;
    }
    return false;
  }

  /**
   * This is the main run method that executes the command rank-towers.
   * 
   * @param command The string of command and arguments.
   * @throws Exception If there is an error accessing the server or user input errors.
   */
  @Override
  public void run(String command) throws Exception {

    String[] args = command.split(" ");

    if (isValid(command)) {

      List<String> ydm = new ArrayList<String>();
      List<Data> list = new ArrayList<Data>();

      Double energy;
      String towerRegex = "^(Mokihana|Ilima|Lehua|Lokelani)$";
      String start = args[1];
      String end = args[2];
      boolean validStart = VerifyDate.isValidDate(start);
      boolean validEnd = VerifyDate.isValidDate(end);

      // Proceed only if the user input valid dates.
      if (validStart && validEnd) {

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
        List<Source> sources = this.client.getSources();
        Source testSource = sources.get(0);
        XMLGregorianCalendar now =
            this.client.getLatestSensorData(testSource.getName()).getTimestamp();
        XMLGregorianCalendar now2 =
            this.client.getLatestSensorData(testSource.getName()).getTimestamp();
        int thisMonth = now.getMonth();
        int today = now.getDay();
        XMLGregorianCalendar startTime =
            DailyEnergy.setDay(now, startYear, startMonth, startDay, 0, 0, 0, 0);
        XMLGregorianCalendar endTime =
            DailyEnergy.setDay(now2, endYear, endMonth, endDay, 23, 59, 59, 999);
        // Check to see if input date is before today's date.
        if (endTime.getMonth() <= thisMonth && endTime.getDay() < today) {
          for (Source source : sources) {
            String sourceName = source.getName();
            if (sourceName.matches(towerRegex)) {
              energy = this.client.getEnergyConsumed(sourceName, startTime, endTime, 0);
              list.add(new Data(sourceName, (int) Math.round(energy)));
            }
          } // End for each source
          Collections.sort(list, new SortByEnergy());
          System.out.format("For the interval %s to %s, energy consumption by tower was:%n", start,
              end);
          for (Data data : list) {
            System.out.format("%8s  %s kWh%n", data.getSource(), data.getEnergy() / 1000);
          }
        }
        else {
          System.out.println("Date must be before today.");
        }
      }
      else {
        System.out.format("Invalid date or date format for at least one argument.");
        return;
      }
    }
    else {
      System.out.print("\n Incorrect number of arguments for rank-towers.");
      System.out.print("\n Format is: \"rank-towers yyyy-MM-dd yyyy-MM-dd\"");
    }
  }
} // End RankTowers
