package org.wattdepot.simpleapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.resource.source.jaxb.Source;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * A simple application that shows basic interaction with a running WattDepot server.
 * @author Philip Johnson
 */
public class SimpleApplication {
  
  /** Holds the WattDepotClient instance used to interact with this server. */
  private WattDepotClient client;
  
  /**
   * Instantiates this class and ensures that the passed URL identifies a running client. 
   * @param url A URL that should point to a running WattDepot server.
   * @throws Exception If the URL does not appear to point to a running WattDepot server.
   */
  public SimpleApplication(String url) throws Exception {
    this.client = new WattDepotClient(url);
    if (!this.client.isHealthy()) {
      throw new Exception("WattDepot URL does not point to a server: " + url);
    }
  }
  
  /**
   * Returns the latency in seconds associated with the passed sensor data instance. 
   * @param data The sensor data item.
   * @return The number of seconds since that data was recorded.
   */
  private long getSensorDataLatency(SensorData data) {
    XMLGregorianCalendar timestamp = data.getTimestamp();
    long now = Calendar.getInstance(Locale.US).getTimeInMillis();
    long latency = now - timestamp.toGregorianCalendar().getTimeInMillis();
    return (latency / 1000);
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
    long latency = getSensorDataLatency(data);
    if (latency > 60) {
      throw new Exception("Sensor Data item has latency over 1 minute.");
    }
    return data.getPropertyAsDouble("powerConsumed");
  }
  
  /** 
   * Returns the daily energy associated with the given source on the given day.
   * @param source  The source. 
   * @param dayString The day of interest in YYYY-MM-DD format.
   * @return The amount of energy consumed by that source on that day.
   * @throws Exception If problems occur obtaining that data from WattDepot.
   */
  public double getDailyEnergy(String source, String dayString) throws Exception {
    Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dayString);
    XMLGregorianCalendar startOfDay = Tstamp.makeTimestamp(date.getTime());
    startOfDay.setTime(0,0,0);
    XMLGregorianCalendar endOfDay = Tstamp.incrementDays(Tstamp.makeTimestamp(date.getTime()), 1);
    endOfDay.setTime(0, 0, 0);
    return client.getEnergyConsumed(source, startOfDay, endOfDay, 0);    
  }
  
  /**
   * Returns the energy consumed for the given source since the start of the given day.
   * @param source The source of interest.
   * @param dayString The start day.
   * @return The total energy consumed in watts since the start day to the latest sensor data. 
   * @throws Exception If problems occur with WattDepot, or the start day is after the latest
   * sensor data. 
   */
  public double getEnergySince(String source, String dayString) throws Exception {
    Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dayString);
    XMLGregorianCalendar startTimestamp = Tstamp.makeTimestamp(date.getTime());
    startTimestamp.setTime(0,0,0);
    XMLGregorianCalendar endTimestamp = client.getLatestSensorData(source).getTimestamp();
    return client.getEnergyConsumed(source, startTimestamp, endTimestamp, 0);    
  }
  
  /**
   * Returns the WattDepotClient associated with this instance. 
   * @return The WattDepotClient.
   */
  public WattDepotClient getClient() {
    return this.client;
  }

  /**
   * Illustrate simple interaction with WattDepot.
   * @param args The first arg should be the URL to a running WattDepot server.
   * @throws Exception If problems occur communicating with the server.
   */
  public static void main(String[] args) throws Exception {
    SimpleApplication application = new SimpleApplication(args[0]);
    WattDepotClient client = application.getClient();

    // Get the list of sources and print them out.
    System.out.println("WattDepot sources defined for this URL:");
    List<Source> sources = client.getSources();
    for (Source source : sources) {
      System.out.format("Name: %20s      %s%n", source.getName(), source.getDescription());
    }
    
    String sourceName = sources.get(0).getName();

    System.out.format("Current power for %s is %,.2f kilowatts.%n", 
        sourceName, (application.getCurrentPower(sourceName) / 1000));
    
    String date = "2011-11-01";
    System.out.format("Daily energy for source %s on %s is %,.2f kilowatts.%n",
        sourceName, date, (application.getDailyEnergy(sourceName, date) / 1000));

    System.out.format("Energy since %s for source %s is %,.2f kWh.%n",
        date, sourceName, (application.getEnergySince(sourceName, date) / 1000));


  }
}
