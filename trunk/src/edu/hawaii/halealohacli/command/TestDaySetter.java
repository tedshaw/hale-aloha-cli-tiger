package edu.hawaii.halealohacli.command;

import static org.junit.Assert.assertEquals;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

/**
 * Test class for DaySetter.
 * 
 * @author Terrence Chida
 * 
 */
public class TestDaySetter {
  DatatypeFactory df = null;

  XMLGregorianCalendar xgc;

  GregorianCalendar gc = new GregorianCalendar();

  /**
   * Test the setDay method to make sure that the date comes out with the
   * correct format.
   */
  @Test
  public void testSetDay() {
    
    // create a generic XMLGregorianCalendar date to set.
    try {
      df = DatatypeFactory.newInstance();
    }
    catch (DatatypeConfigurationException dce) {
      throw new IllegalStateException(
          "Exception while obtaining DatatypeFactory instance", dce);
    }
    xgc = df.newXMLGregorianCalendar(gc);
    
    // test to see that date is set correctly.
    assertEquals("Check date.",
        DaySetter.setDay(xgc, 2011, 11, 11, 11, 11, 11, 111).toString(),
        "2011-11-11T11:11:11.111-10:00");
  } // end testSetDay().

} // end TestDaySetter.
