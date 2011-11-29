package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertFalse;
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
  
  // Test cases which should indicate passes.
  String pass1 = "daily-energy Mokihana 2011-11-24";
  
  // Test cases which should indicate failures.
  String fail1 = "blah";

  /**
   * Test the isValid() method.
   */
  @Test
  public void testIsValid() {
    assertTrue("Check number of arguments", energyTest.isValid(pass1));
    assertFalse("Check number of arguments", energyTest.isValid(fail1));
  } // end testIsValid()
  
} // end TestDailyEnergy
