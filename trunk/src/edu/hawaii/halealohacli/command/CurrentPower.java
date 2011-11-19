package edu.hawaii.halealohacli.command;

import java.util.Calendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;

/**
 * Gets the current power for a tower or a lounge.
 * @author Matthew Mizumoto
 *
 */
public class CurrentPower {
  
  /** Holds the WattDepotClient instance used to interact with this server. */
  private WattDepotClient client;
  
  /**
   * Instantiates this class and ensures that the passed URL identifies a running client. 
   * @param url A URL that should point to a running WattDepot server.
   * @throws Exception If the URL does not appear to point to a running WattDepot server.
   */
  public CurrentPower(String url) throws Exception {
    this.client = new WattDepotClient(url);
    if (!this.client.isHealthy()) {
      throw new Exception("WattDepot URL does not point to a server: " + url);
    }
  }

  /**
   * Get the latest recorded power for a source.
   * Throws an exception if this data is more than one minute old.
   * This method is adapted from Phil Johnson's sample code
   * @param source The source of interest.
   * @return The latest recorded power.
   * @throws Exception If latency of latest power exceeds 60 seconds. 
   */
  public double getCurrentPower (String source) throws Exception {
    SensorData data = client.getLatestSensorData(source);
    return data.getPropertyAsDouble("powerConsumed");
  }
  
  /**
   * Returns the WattDepotClient associated with this instance. 
   * Method modified from Phil Johnson's sample code.
   * @return The WattDepotClient.
   */
  public WattDepotClient getClient() {
    return this.client;
  }

  /** Gets the current power of a tower or lounge.
  * @param args The first arg should be the URL to a running WattDepot server.
  * @throws Exception If problems occur communicating with the server.
  */
  public static void main (String[] args) throws Exception  {
    CurrentPower application = new CurrentPower(args[0]);
    
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

    String sourceName = args[1];
    
    System.out.format("Current power for  %s as of %s %s is %,.2f kilowatts.%n", 
        sourceName, nowDate, nowTime, (application.getCurrentPower(sourceName) / 1000));
        
  }

}