package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for MonitorPower command class.
 * 
 * @author Ted Shaw
 * 
 */
public class TestMonitorPower {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  MonitorPower monitorPower = new MonitorPower(testClient);

  /**
   * Tests the validation of the number of arguments for rank-towers command.
   */
  @Test
  public void testValid() {
    assertTrue("Number of commands is valid", monitorPower.isValid("monitor-power Ilima 9"));
    assertFalse("Number of commands is invalid", monitorPower.isValid("monitor-power"));
  }

}
