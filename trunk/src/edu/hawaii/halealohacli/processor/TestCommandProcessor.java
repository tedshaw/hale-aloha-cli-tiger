package edu.hawaii.halealohacli.processor;

import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * JUnit test cases for CommandProcessor.
 * 
 * @author Toy Lim
 */
public class TestCommandProcessor {
  WattDepotClient testClient = new WattDepotClient("http://server.wattdepot.org:8190/wattdepot/");
  CommandProcessor commandProcessor = new CommandProcessor(testClient);

  /**
   * Test method for
   * {@link edu.hawaii.halealohacli.processor.CommandProcessor#run(java.lang.String)}.
   */
  @Test
  public void testRun() {
    try {
      commandProcessor.run("help");
      commandProcessor.run("current-power Ilima");
      commandProcessor.run("daily-energy");
      commandProcessor.run("energy-since Ilima 2011-11-23");
      commandProcessor.run("rank-towers");
      commandProcessor.run("set-baseline");
      commandProcessor.run("monitor-power");
      commandProcessor.run("monitor-goal");
      commandProcessor.run("quit");
    }
    catch (Exception e) {
      // e.printStackTrace();
      System.err.println("Exception occurred.");
      return;
    }
  }

}
