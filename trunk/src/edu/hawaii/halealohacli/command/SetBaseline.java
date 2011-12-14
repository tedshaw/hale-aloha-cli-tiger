package edu.hawaii.halealohacli.command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.BadXmlException;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Implement the set-baseline Command interface. <br/>
 * <br/>
 * 
 * This command defines [date] as the "baseline" day for [tower | lounge]. [date] is an optional
 * argument in YYYY-MM-DD format and defaults to yesterday. When this command is executed, the
 * system should obtain and save the amount of energy used during each of the 24 hours of that day
 * for the given tower or lounge. These 24 values define the baseline power for that tower or lounge
 * for that one hour time interval. For example, if lounge Ilima-A used 100 kWh of energy during the
 * hour 6am-7am, then the baseline power during the interval 6am - 7am for Ilima-A is 100 kW. <br/>
 * 
 * Usage: set-baseline [tower | lounge] [date] <br/>
 * Set the data from the date (yyyy-mm-dd) as the baseline power for goal monitoring. <br/>
 * <br/>
 * 
 * Note: <br/>
 * Towers are: Mokihana, Ilima, Lehua, Lokelani <br/>
 * Lounges are the tower names followed by a "-" followed by one of A, B, C, D, E. For example,
 * Mokihana-A.
 * 
 * @author Toy Lim
 */
public class SetBaseline implements Command {
  private WattDepotClient wattDepotClient;

  /**
   * Default constructor.
   * 
   * @param client WattDepotClient to be used by this command
   * @see org.wattdepot.client.WattDepotClient
   */
  public SetBaseline(WattDepotClient client) {
    wattDepotClient = client;
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
      System.err.println("Usage: set-baseline [tower | lounge] [date]");
      return false;
    }
    if (!"set-baseline".equals(cmd[0])) {
      return false;
    }
    if (cmd.length > 2) {
      String date = cmd[2];
      try {
        Tstamp.makeTimestamp(date);
      }
      catch (Exception e) {
        System.err.format("Invalid date: %s\n", date);
        return false;
      }
    }
    return true;
  }

  /**
   * Execute the command.
   * 
   * @param command String containing both the command and its parameters
   * @throws Exception thrown whenever an error is detected
   * @see edu.hawaii.halealohacli.command.Command#run(java.lang.String)
   */
  @Override
  public void run(String command) throws Exception {
    if (!isValid(command)) {
      return;
    }
    String[] cmd = command.split(" ");
    String source = cmd[1];
    String filename = source + ".xml";
    XMLGregorianCalendar startTime = null;
    String date = null;
    if (cmd.length > 2) {
      date = cmd[2];
      startTime = Tstamp.makeTimestamp(date);
    }
    else {
      startTime = Tstamp.makeTimestamp();
      startTime.setHour(0);
      startTime.setMinute(0);
      startTime.setSecond(0);
      startTime.setMillisecond(0);
      startTime =
          Tstamp.makeTimestamp(startTime.toGregorianCalendar().getTimeInMillis()
              - (1000L * 60 * 60 * 24));
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      date = format.format(new Date(startTime.toGregorianCalendar().getTimeInMillis()));
    }

    Baseline baselineData = new Baseline(source);
    XMLGregorianCalendar hourStart = null, hourStop = null;
    hourStart = startTime;
    for (int i = 1; i <= 24; i++) {
      hourStop = Tstamp.incrementHours(startTime, i);

      try {
        Double energy = wattDepotClient.getEnergyConsumed(source, hourStart, hourStop, 0);
        baselineData.setBaseline(i - 1, energy);
      }
      catch (BadXmlException e) {
        XMLGregorianCalendar firstData = null;
        try {
          firstData = wattDepotClient.getSourceSummary(source).getFirstSensorData();
        }
        catch (WattDepotClientException e1) {
          System.err.println("Error attempting to access data from " + source);
          return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        System.err.format(
            "Error attempting to access data from date. Please use a date after %s\n",
            format.format(new Date(firstData.toGregorianCalendar().getTimeInMillis())));
        return;
      }
      catch (WattDepotClientException e) {
        System.err.format("Error attempting to access data from %s\n", source);
        return;
      }

      hourStart = hourStop;
    }
    baselineData.storeToFile(filename);
    System.out.format("Successfully stored baseline data for %s on %s in file %s.\n", source, date,
        filename);
  }
}
