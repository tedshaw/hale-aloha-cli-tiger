package edu.hawaii.halealohacli.command;

import java.util.Calendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;

/**
 * Gets the current power for a tower or a lounge.
 * 
 * @author Matthew Mizumoto
 * 
 */
public class CurrentPower implements Command {

  /** Holds the WattDepotClient instance used to interact with this server. */
  private WattDepotClient client;

  /**
   * Instantiates this class and ensures that the passed URL identifies a running client.
   * 
   * @param client The WattDepot client.
   */
  public CurrentPower(WattDepotClient client) {
    this.client = client;
  }

  /**
   * Get the latest recorded power for a source. Throws an exception if this data is more than one
   * minute old. This method is adapted from Phil Johnson's sample code
   * 
   * @param source The source of interest.
   * @return The latest recorded power.
   * @throws Exception If latency of latest power exceeds 60 seconds.
   */
  public double getCurrentPower(String source) throws Exception {
    SensorData data = client.getLatestSensorData(source);
    return data.getPropertyAsDouble("powerConsumed");
  }

  /**
   * Gets the current power of a tower or lounge.
   * 
   * @param command The first arg should be the URL to a running WattDepot server.
   * @throws Exception If problems occur communicating with the server.
   */
  @Override
  public void run(String command) throws Exception {

    CurrentPower application = new CurrentPower(this.client);
    String[] commandAndArgs = command.split(" ");
    
    Calendar cal = Calendar.getInstance();
    DatatypeFactory dtf = DatatypeFactory.newInstance();
    XMLGregorianCalendar nowTime = dtf.newXMLGregorianCalendar();
    XMLGregorianCalendar nowDate = dtf.newXMLGregorianCalendar();
    
    nowDate.setYear(cal.get(Calendar.YEAR));
    nowDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
    nowDate.setMonth(cal.get(Calendar.MONTH) + 1);
    
    nowTime.setHour(cal.get(Calendar.HOUR_OF_DAY));
    nowTime.setMinute(cal.get(Calendar.MINUTE) - 1);
    nowTime.setSecond(cal.get(Calendar.SECOND));

    System.out.format("Current power for  %s as of %s %s is %,.2f kilowatts.%n", commandAndArgs[1],
        nowDate, nowTime, (application.getCurrentPower(commandAndArgs[1]) / 1000));
  }

  @Override
  public boolean isValid(String command) {
    ValidateLocation validlocate = new ValidateLocation();

    
    String[] splitCommand = command.split(" ");
    if (splitCommand.length == 2 && validlocate.isValid(splitCommand[1]))  {
      return true;
    }
    return false;
  }

}