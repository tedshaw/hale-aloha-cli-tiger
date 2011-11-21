/**
 * 
 */
package edu.hawaii.halealohacli.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.util.tstamp.Tstamp;
import org.wattdepot.client.WattDepotClientException;

//import org.wattdepot.server.Server;

/**
 * Implements the "energy-since" command class.
 * 
 * @author Ardell Klemme
 * 
 */
public class EnergySince implements Command {

  // allows the command to validate the location given.
  ValidateLocation validLoc = new ValidateLocation();
  // client for this command class, to be set upon call to constructor.
  WattDepotClient client;

  /**
   * Executes the command, returning the energy consumed since the date provided for a specific
   * lounge and tower.
   * 
   * @see edu.hawaii.halealohacli.command.Command#run(java.lang.String[])
   * @param parameters The string sent to run from CommandProcessor class.
   * @throws Exception If there are problems getting date information and power data from server.
   */
  @Override
  public void run(String parameters) throws Exception {
    
    String[] commandWithParameters = parameters.split(" ");
    String area = commandWithParameters[1];

    // get lastest date and time
    SensorData pinged = this.client.getLatestSensorData(area);
    XMLGregorianCalendar lastGregDate = pinged.getTimestamp();
    String lastStrTime =  lastGregDate.getHour() + ":" + lastGregDate.getMinute() + ":" +
        lastGregDate.getSecond();
    String lastStrDate = lastGregDate.getYear() + "-" + lastGregDate.getMonth() + "-" +
        lastGregDate.getDay();

    //create search date variable
    String sDate = commandWithParameters[2];
    XMLGregorianCalendar searchDate = null;
    Date pastDate = null;
    double powerUsed = 0.0;

    // basic error checking.
    if (!validLoc.isValid(area)) {
      System.out.println("Location provided is not a tower or lounge!");
    }
    
    try {
      pastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(sDate);
      searchDate = Tstamp.makeTimestamp(pastDate.getTime());
      searchDate.setTime(0, 0, 0);
    }
    catch (ParseException e) {
      // TODO Auto-generated catch block.
      e.printStackTrace();
    }

    // calculate power since.
    try {
      powerUsed = getEnergyConsumed(area, searchDate, lastGregDate, 0);
    }
    catch (WattDepotClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.format("Total energy consumption by %s 00:00:00 from %s to %s %s is: %f kWh. \n", 
        area, sDate, lastStrDate, lastStrTime, powerUsed);
  }

  /**
   * Basic method to get amount of energy consumed over a span of time.
   * 
   * @param source The location for energy data.
   * @param startTime Given as a parameter from command processor.
   * @param endTime The latest sensor reading time.
   * @param samplingInterval how frequent to sample, zero here as we want a total.
   * @return double value of kWh used within the time period.
   * @throws WattDepotClientException If there is a problem communicating with the server.
   */
  public double getEnergyConsumed(String source, XMLGregorianCalendar startTime,
      XMLGregorianCalendar endTime, int samplingInterval) throws WattDepotClientException {
    return this.client.getEnergyConsumed(source, startTime, endTime, samplingInterval);
  }

  /**
   * Initializes the EnergySince class with a WattDepotClient that is passed from the Command
   * Processor class after it has been checked for a healthy connection.
   * 
   * @param client The client to be used for this command.
   */
  public EnergySince(WattDepotClient client) {
    this.client = client;
  }

  @Override
  public boolean isValid(String command) {
    String[] splitCommand = command.split(" ");
    if (splitCommand.length == 3) {
      return true;
    }
    return false;
  }
}
