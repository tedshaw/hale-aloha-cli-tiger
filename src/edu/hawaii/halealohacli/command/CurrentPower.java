package edu.hawaii.halealohacli.command;

import java.util.List;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.resource.source.jaxb.Source;

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
    WattDepotClient client = application.getClient();

    
   /* Calendar cal = Calendar.getInstance();
    DatatypeFactory dtf = DatatypeFactory.newInstance();
    XMLGregorianCalendar now = dtf.newXMLGregorianCalendar(); 
    now.setYear(cal.get(Calendar.YEAR));
    now.setDay(cal.get(Calendar.DAY_OF_MONTH));
    now.setMonth(cal.get(Calendar.MONTH) + 1);
    now.setHour(cal.get(Calendar.HOUR_OF_DAY));
    now.setMinute(cal.get(Calendar.MINUTE) - 1);
    now.setSecond(cal.get(Calendar.SECOND)); */
    
    List<Source> sources = client.getSources();
    String sourceName = sources.get(0).getName();

    System.out.format("Current power for %s is %,.2f kilowatts.%n", 
        sourceName, (application.getCurrentPower(sourceName) / 1000));
        
  }

}
