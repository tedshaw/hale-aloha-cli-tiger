package edu.hawaii.halealohacli.command;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for RankTowers command class.
 * 
 * @author Ardell Klemme
 *
 */
public class RankTowersTest {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  RankTowers rankTest = new RankTowers(testClient);
  


  /**
   * Tests the validation of the number of arguments for rank-towers command.
   */
  @Test
  public void testValid() {
    assertTrue("Number of commands is valid", 
        rankTest.isValid("rank-towers 2011-09-01 2011-11-01") );
    assertFalse("Number of commands is invalid", rankTest.isValid("energy-since") );
  }

}
