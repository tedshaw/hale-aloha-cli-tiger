package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Validates that a certain location exists on the WattDepot Server.
 * 
 * @author Ardell Klemme
 *
 */
public class ValidateLocationTest {
  ValidateLocation newTest = new ValidateLocation();

  /**
   * Ensures that all towers are included in the ArrayList.
   */
  @Test
  public void testTowers() {
    
    assertTrue("Lehua Tower is included in the ArrayList", newTest.isValid("Lehua") );
    assertTrue("Ilima Tower is included in the ArrayList", newTest.isValid("Ilima") );
    assertTrue("Mokihana Tower is included in the ArrayList", newTest.isValid("Mokihana") );
    assertTrue("Lokelani Tower is included in the ArrayList", newTest.isValid("Lokelani") );
    assertFalse("MrNoName Tower does not exist.", newTest.isValid("MrNoName"));
  }
  
  /**
   * Ensures that lounges are available in the ArrayList by checking first and last lounge alphas.
   */
  @Test
  public void testLounges() {
    
    assertTrue("First lounge for Lehua is included in the ArrayList", newTest.isValid("Lehua-A") );
    assertTrue("Last lounge for is included in the ArrayList", newTest.isValid("Lehua-E") );
    assertFalse("Lounge F does not exist for tower Lehua", newTest.isValid("Lehua-F"));
  }

}
