package org.wattdepot.simpleapp;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * A simple application that shows basic interaction with a running WattDepot server.
 * @author Philip Johnson
 */
public class SimpleApplication {

  /**
   * Illustrate simple interaction with WattDepot.
   * @param args The first arg should be the URL to a running WattDepot server.
   * @throws Exception If problems occur communicating with the server.
   */
  public static void main(String[] args) throws Exception {
    String url = args[0];
    // Create a client.
    WattDepotClient client = new WattDepotClient(url);
    // Check to make sure a connection can be made. 
    // If no connection, then exit right now.
    if (client.isHealthy()) {
      System.out.format("Connected successfully to: %s%n", url);
    }
    else {
      System.out.format("Could not connect to: %s%n", url);
      return;
    }
    // Get the list of sources and print them out.
    System.out.println("WattDepot sources defined for this URL:");
    List<Source> sources = client.getSources();
    for (Source source : sources) {
      System.out.format("Name: %20s      %s%n", source.getName(), source.getDescription());
    }
    
    // Find the last sensor data from the first source in our list.
    String sourceName = sources.get(0).getName();
    SensorData data = client.getLatestSensorData(sourceName);
    
    // Show the sensor data item
    System.out.format("Latest energy data from source %s is %s.%n", sourceName, data);
    
    // Let's figure out how long ago this energy information occurred.
    XMLGregorianCalendar timestamp = data.getTimestamp();
    long now = Calendar.getInstance(Locale.US).getTimeInMillis();
    long latency = now - timestamp.toGregorianCalendar().getTimeInMillis();
    System.out.format("This data is %s seconds old.", (latency / 1000));
  }
}
