package edu.hawaii.halealohacli.command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * This command prints out a timestamp and the current power for [tower | lounge] every [interval]
 * seconds. [interval] is an optional integer greater than 0 and defaults to 10 seconds. Entering
 * any character (such as a carriage return) stops this monitoring process and returns the user to
 * the command loop.
 * 
 * @author Ted Shaw
 * 
 * 
 */
public class MonitorPower implements Command {

  /**
   * Default constructor.
   * 
   * @param client WattDepotClient to be used by this command
   * @see org.wattdepot.client.WattDepotClient
   */
  public MonitorPower(WattDepotClient client) {
    // wattDepotClient = client;
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
    XMLGregorianCalendar startTime = null, endTime = null, tmpTime = null;
    if (cmd.length > 2) {
      String date = cmd[2];
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
    }
    endTime = Tstamp.incrementDays(startTime, 1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    for (int i = 1; i <= 24; i++) {
      tmpTime = Tstamp.incrementHours(startTime, i);
      System.out.format("%s \t%s \t%s \t%s\n", source,
          format.format(new Date(startTime.toGregorianCalendar().getTimeInMillis())),
          format.format(new Date(tmpTime.toGregorianCalendar().getTimeInMillis())),
          format.format(new Date(endTime.toGregorianCalendar().getTimeInMillis())));
    }
  }

}