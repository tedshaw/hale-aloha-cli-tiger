package edu.hawaii.halealohacli.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;

/**
 * Used for obtaining the amount of energy used on a particular day.
 * 
 * @author Terrence Chida
 * 
 */
public class DailyEnergy implements Command {

  // client for this command class, to be set upon call to constructor.
  WattDepotClient client;

  /**
   * Initializes the DailyEnergy class with a WattDepotClient that is passed
   * from the Command Processor class after it has been checked for a healthy
   * connection.
   * 
   * @param client
   *          The client to be used for this command.
   */
  public DailyEnergy(WattDepotClient client) {
    this.client = client;
  }

  /**
   * Verifies whether or not command has the correct number of arguments.
   * 
   * @param command
   *          The string of command arguments.
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
   * Runs the program.
   * 
   * @param command
   *          Command line arguments.
   * @throws Exception
   *           If there is an error accessing the server or user input errors.
   */
  @Override
  public void run(String command) throws Exception {
    String[] commandAndArgs = command.split(" ");

    if (isValid(command)) {
      List<String> ydm = new ArrayList<String>();
      Double energy;
      String tower = commandAndArgs[1];
      String date = commandAndArgs[2];
      if (VerifyDate.isValidDate(date)) {
        StringTokenizer st = new StringTokenizer(date, "-");
        while (st.hasMoreTokens()) {
          ydm.add(st.nextToken());
        }
        Integer year = Integer.parseInt(ydm.get(0));
        Integer month = Integer.parseInt(ydm.get(1));
        Integer day = Integer.parseInt(ydm.get(2));
        XMLGregorianCalendar now = client.getLatestSensorData(tower)
            .getTimestamp();
        int thisMonth = now.getMonth();
        int today = now.getDay();
        XMLGregorianCalendar now2 = client.getLatestSensorData(tower)
            .getTimestamp();
        XMLGregorianCalendar start = DaySetter.setDay(now, year, month, day,
            0, 0, 0, 0);
        XMLGregorianCalendar end = DaySetter.setDay(now2, year, month, day,
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
      } // end verify date
      else {
        System.out.println("Sorry, you have entered an invalid date.");
      }
    } // end if command is valid.
    else {
      System.out.println("The command is not a valid one.");
    }

  } // end run().


} // end DailyEnergy
