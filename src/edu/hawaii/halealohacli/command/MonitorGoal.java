package edu.hawaii.halealohacli.command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;

/**
 * This command prints out a timestamp, the current power being consumed by the [tower | lounge],
 * and whether or not the lounge is meeting its power conservation goal. [goal] is an integer
 * between 1 and 99. It defines the percentage reduction from the baseline for this [tower | lounge]
 * at this point in time. [interval] is an integer greater than 0.
 * 
 * @author Ted Shaw
 * 
 * 
 */
public class MonitorGoal implements Command {
  WattDepotClient client;

  /**
   * Default constructor.
   * 
   * @param client WattDepotClient to be used by this command
   * @see org.wattdepot.client.WattDepotClient
   */
  public MonitorGoal(WattDepotClient client) {
    this.client = client;
  }

  /**
   * Checks if command parameters are valid.
   * 
   * @param command String containing both the command and its parameters to be validated
   * @return true if valid, false otherwise
   * @see edu.hawaii.halealohacli.command.Command#isValid(java.lang.String)
   */
  @Override
  public boolean isValid(String command) {
    String[] cmd = command.split(" ");
    if (cmd.length < 2) {
      System.err.println("Insufficient number of arguments given.");
      System.err.println("Usage: monitor-power [tower | lounge] [interval]");
      return false;
    }
    if (!"monitor-power".equals(cmd[0])) {
      return false;
    }
    if (cmd.length > 2) {
      String intervalString = cmd[2];
      int interval = Integer.parseInt(intervalString);
      if (interval < 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Basic run method that will run the command accepting list of strings as parameters.
   * 
   * @param command command needed to run the program
   * @throws Exception this is a catch-all for exceptions. We'll generate more detailed exceptions
   * at a later date.
   */
  public void run(String command) throws Exception {
    if (!isValid(command)) {
      return;
    }
    String[] cmd = command.split(" ");
    String source = cmd[1];
    long interval;
    if (cmd.length > 2) {
      String intervalString = cmd[2];
      interval = Integer.parseInt(intervalString) * 1000;
    }
    else {
      interval = 10 * 1000;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    System.out.format(
        "Retrieving current power for %s every %s seconds.\nPress Enter to stop.\n\n", source,
        interval / 1000);

    SensorData data;
    XMLGregorianCalendar latestTime;
    String currentTime = null;
    double power = 0;

    while (System.in.available() == 0) {
      data = client.getLatestSensorData(source);
      latestTime = data.getTimestamp();
      currentTime = format.format(new Date(latestTime.toGregorianCalendar().getTimeInMillis()));
      power = data.getPropertyAsDouble("powerConsumed") / 1000;
      System.out.format("%s's power consumption at %s is: %.2f kW.\n", source, currentTime, power);
      for (int i = 0; i < interval * 4 / 1000 && System.in.available() == 0; i++) {
        Thread.sleep(250);
      }
    }

    Scanner keybd = new Scanner(System.in);
    keybd.nextLine();

  }
}