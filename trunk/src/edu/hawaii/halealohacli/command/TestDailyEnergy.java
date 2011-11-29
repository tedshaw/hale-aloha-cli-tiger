package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Test class for DailyEnergy.java.
 * @author Terrence Chida
 *
 */
public class TestDailyEnergy {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  DailyEnergy energyTest = new DailyEnergy(testClient);
  String testCommand = "daily-energy Mokihana 2011-11-24";

  /**
   * Test the isValid() method.
   */
  @Test
  public void testIsValid() {
    assertTrue("Check number of arguments", energyTest.isValid(testCommand));
  } // end testIsValid()

} // end TestDailyEnergy
