/**
 * 
 */
package edu.hawaii.halealohacli.command;

import java.util.SortedSet;

/**
 * Verifies whether or not a given location is either a tower or lounge.
 * 
 * @author Ardell Klemme
 *
 */
public class ValidateLocation {
  
  private static final String[] TOWERS = {"Mokihana", "Ilima", "Lehua", "Lokelani"};
  private static final String[] ALPHA = {"-A", "-B", "-C", "-D", "-E"};
  private static SortedSet<String> locations = null;
  
  /**
   * Generates a complete listing of all valid location arguments to check arguments against.
   * 
   * @return SortedSet<String> locations - a collection of all possible valid location arguments.
   */
  public SortedSet<String> availableLounges() {
    
    for (String tower : TOWERS) {
      
      for (String location : ALPHA) {
        locations.add(tower + location);   
      }
      
      locations.add(tower);
    }
    return locations;
  }
  
  /**
   * Checks to see if a string argument is contained in the list of all valid locations.
   * 
   * @param argument is the user submitted location [tower | lounge].
   * @return boolean value of whether or not the location is valid.
   */
  public static boolean isValid (String argument) {
    return locations.contains(argument);
  }
  
  /**
   * Default constructor for ValidateLocation class.
   * 
   * @param location is to be validated by the class.
   */
  public ValidateLocation(String location) {
    //default constructor
  }
}
