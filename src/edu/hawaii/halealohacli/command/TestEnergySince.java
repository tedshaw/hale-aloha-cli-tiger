package edu.hawaii.halealohacli.command;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for EnergySince command class.
 * 
 * @author Ardell Klemme
 *
 */
public class TestEnergySince {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  EnergySince energyTest = new EnergySince(testClient);
  


  /**
   * Tests the validation of the number of arguments for energy-since command.
   */
  @Test
  public void testValid() {
    assertTrue("Number of commands is valid", energyTest.isValid("energy-since Lehua 2011-11-01") );
    assertFalse("Number of commands is invalid", energyTest.isValid("energy-since") );
  }
  
  /**
   * Test to make sure that the checkTens method creates a string that is of the correct format.
   */
  @Test
  public void testCheckTens() {
    assertEquals("Make sure 07 shows up as 07 and not 7", "07", energyTest.checkTens(7));
    assertEquals("Make sure 17 shows up as 17", "17", energyTest.checkTens(17));
  }
}
