package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for MonitorGoal command class.
 * 
 * @author Ted Shaw
 * 
 */
public class TestMonitorGoal {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  MonitorGoal monitorGoal = new MonitorGoal(testClient);

  /**
   * Tests the validation of the number of arguments for rank-towers command.
   */
  @Test
  public void testValid() {
    assertTrue("Number of commands is valid", monitorGoal.isValid("monitor-goal Ilima 85 10"));
    assertFalse("Number of commands is invalid", monitorGoal.isValid("monitor-goal"));
  }

}
