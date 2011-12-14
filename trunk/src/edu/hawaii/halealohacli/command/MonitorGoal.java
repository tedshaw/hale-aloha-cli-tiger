package edu.hawaii.halealohacli.command;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.BadXmlException;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.client.WattDepotClientException;
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
    if (cmd.length < 3) {
      System.err.println("Insufficient number of arguments given.");
      System.err.println("Usage: monitor-goal [tower | lounge] [goal] [interval]");
      return false;
    }
    if (!"monitor-goal".equals(cmd[0])) {
      return false;
    }

    String goalString = cmd[2];
    int goal;
    try {
      goal = Integer.parseInt(goalString);
    }
    catch (NumberFormatException e) {
      // e1.printStackTrace();
      System.err.format("Invalid goal: %s\n", goalString);
      return false;
    }
    if (goal < 1 || goal > 99) {
      System.err.format("Goal out of range: %d (1 to 99)\n", goal);
      return false;
    }

    if (cmd.length > 3) {
      String intervalString = cmd[3];
      int interval;
      try {
        interval = Integer.parseInt(intervalString) * 1000;
      }
      catch (NumberFormatException e) {
        // e.printStackTrace();
        System.err.format("Invalid interval: %s\n", intervalString);
        return false;
      }
      if (interval < 0) {
        System.err.format("Invalid interval: %d\n", interval / 1000);
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
    String goalString = cmd[2];

    int goal = Integer.parseInt(goalString);

    long interval = 0;
    if (cmd.length > 3) {
      String intervalString = cmd[3];
      interval = Integer.parseInt(intervalString) * 1000;
    }
    else {
      interval = 10 * 1000;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    System.out.format(
        "Retrieving current power for %s every %s seconds.\n[Press Enter to stop.]\n\n", source,
        interval / 1000);

    SensorData data;
    XMLGregorianCalendar latestTime;
    String dataTime = null;
    double currentPower = 0;
    double basePower = 0;
    String metGoal;

    Date date = new Date();
    Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
    calendar.setTime(date); // assigns calendar to given date
    int timeIndex = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format

    Baseline base = new Baseline(source);

    if (!base.retrieveFromFile(source + ".xml")) {
      System.err.println("A prior baseline power has not been set. "
          + "Please use the set-baseline command first.");
      return;
    }

    while (System.in.available() == 0) {
      try {
        data = client.getLatestSensorData(source);
        latestTime = data.getTimestamp();
        dataTime = format.format(new Date(latestTime.toGregorianCalendar().getTimeInMillis()));
        currentPower = data.getPropertyAsDouble("powerConsumed") / 1000;
        basePower = base.getBaseline(timeIndex) / 1000;
        if (currentPower <= basePower * (100 - goal) / 100) {
          metGoal = "Goal met.";
        }
        else {
          metGoal = "Goal not met.";
        }
        System.out.format("%s's current power at %s is: %.2f kW. Base power is: %.2f kW. %s\n",
            source, dataTime, currentPower, basePower, metGoal);
        for (int i = 0; i < interval * 4 / 1000 && System.in.available() == 0; i++) {
          Thread.sleep(250);
        }
      }
      catch (BadXmlException e) {
        XMLGregorianCalendar firstData = null;
        try {
          firstData = client.getSourceSummary(source).getFirstSensorData();
        }
        catch (WattDepotClientException e1) {
          System.err.println("Error attempting to access data from " + source);
          return;
        }
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        System.err.format(
            "Error attempting to access data from date. Please use a date on or after %s\n",
            format.format(new Date(firstData.toGregorianCalendar().getTimeInMillis())));
        return;
      }
      catch (WattDepotClientException e) {
        System.err.format("Error attempting to access data from %s\n", source);
        return;
      }
    }

    Scanner keybd = new Scanner(System.in);
    keybd.nextLine();
  }

}