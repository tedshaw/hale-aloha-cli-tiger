package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.wattdepot.client.WattDepotClient;

/**
 * Junit test class for Baseline class.
 * 
 * @author Ted Shaw
 * 
 */
public class TestBaseline {
  Baseline defaultBase = new Baseline();
  Baseline baseline = new Baseline("Ilima");

  /**
   * Tests getBaseline method.
   */
  @Test
  public void testGetBaseline() {
    assertEquals(0.0, baseline.getBaseline(1), 0.001);
    baseline.getBaseline(30);
  }

  /**
   * Tests setBaseline method.
   */
  @Test
  public void testSetBaseline() {
    baseline.setBaseline(1);
    defaultBase.setBaseline(1);
    baseline.setBaseline(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4);
  }

  /**
   * Tests storeToFile method.
   */
  @Test
  public void testStoreToFile() {
    baseline.storeToFile("Ilima.xml");
  }
  
  /**
   * Tests retrieveFromFile method.
   */
  @Test
  public void testRetrieveFromFile() {
    baseline.retrieveFromFile("Ilima.xml");
    baseline.retrieveFromFile("nofile");
  }
  
}
