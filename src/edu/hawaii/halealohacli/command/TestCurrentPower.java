package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for CurrentPower command class.
 * 
 * @author Matthew Mizumoto
 *
 */
public class TestCurrentPower {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  CurrentPower currentPower = new CurrentPower(testClient);
  

  /**
   * Tests the validation of the number of arguments for energy-since command.
   */
  @Test
  public void testValid() {
    assertTrue("Number of commands is valid", currentPower.isValid("current-power Ilima") );
    assertFalse("Number of commands is invalid", currentPower.isValid("current-power") );
  }

}
