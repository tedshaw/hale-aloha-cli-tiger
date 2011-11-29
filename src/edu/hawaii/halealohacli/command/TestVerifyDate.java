package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for VerifyDate.java.
 * @author Terrence Chida
 *
 */
public class TestVerifyDate {
  
  // cases which should pass
  String pass1 = "2011-11-24";
  
  // cases which should fail
  String fail1 = "2011-11";
  String fail2 = "2012-01-01";
  String fail3 = "2011-1-30";
  String fail4 = "2011-11-3";

  /**
   * Test various cases for isValidDate().
   */
  @Test
  public void verifyDateTest() {
    assertTrue("Check pass1", VerifyDate.isValidDate(pass1));
    assertFalse("Check fail1", VerifyDate.isValidDate(fail1));
    assertFalse("Check fail2", VerifyDate.isValidDate(fail2));
    assertFalse("Check fail3", VerifyDate.isValidDate(fail3));
    assertFalse("Check fail4", VerifyDate.isValidDate(fail4));
  } // end verifyDateTest()

} // end TestVerifyDate
