/**
 * 
 */
package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for EnergySince command class.
 * 
 * @author Ardell Klemme
 *
 */
public class EnergySinceTest {
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

}
