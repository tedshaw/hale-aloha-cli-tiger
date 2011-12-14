package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Junit test class for SetBaseline command. 
 *
 * @author Joshua Antonio
 *
 */
public class TestSetBaseline {
  WattDepotClient client = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  SetBaseline setBaseline = new SetBaseline(client);

  /**
   * Tests isValid() method for the set-baseline command.
   */
  @Test
  public void testIsValid() {
    assertTrue("Number of commands is valid", setBaseline.isValid("set-baseline Ilima"));
    assertFalse("Number of commands is invalid", setBaseline.isValid("set-baseline"));
  }

  /**
   * Tests the run() method for the set-baseline command.
   */
  @Test
  public void testRun() {
    try {
      setBaseline.run("set-baseline Ilima 2011-12-02");
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
