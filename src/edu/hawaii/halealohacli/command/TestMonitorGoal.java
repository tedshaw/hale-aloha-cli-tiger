package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Basic test for MonitorGoal command class. The multi-threaded test code is from
 * http://www.codeguru.com/forum/showthread.php?t=453798
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

  /**
   * Test the run() function.
   */
  @Test
  public void testRun() {
    PipedOutputStream testOutStream = new PipedOutputStream();
    InputStream testInStream;
    try {
      testInStream = new PipedInputStream(testOutStream);
    }
    catch (IOException e) {
      // e1.printStackTrace();
      System.err.println("Error encountered while trying to connect input and output pipe.");
      return;
    }
    PrintStream printOut = new PrintStream(testOutStream);

    InputStream old = System.in;

    System.setIn(testInStream);
    FutureTask<?> theTask = null;
    // create new task
    theTask = new FutureTask<Object>(new Runnable() {
      public void run() {
        try {
          monitorGoal.run("monitor-goal Ilima 1 1");
        }
        catch (Exception e) {
          // e.printStackTrace();
          return;
        }
      }
    }, null);

    // start task in a new thread
    new Thread(theTask).start();

    // let the task thread execute for 5s, then send the termination keyboard input
    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException e) {
      //e.printStackTrace();
      System.err.println("Interrupt Exception occurred.");
      return;
    }
    printOut.println("Terminate");

    // wait for the execution to finish, timeout after 10 secs
    try {
      theTask.get(10L, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      //e.printStackTrace();
      System.err.println("Timeout Exception occurred.");
      return;
    }

    System.setIn(old);

  }

}
