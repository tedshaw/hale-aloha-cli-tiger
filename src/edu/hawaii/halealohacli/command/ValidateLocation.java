package edu.hawaii.halealohacli.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Verifies whether or not a given location is either a tower or lounge.
 * 
 * @author Ardell Klemme
 *
 */
public class ValidateLocation {
  
  private static final List<String> TOWERS = 
      Collections.unmodifiableList(Arrays.asList("Mokihana", "Ilima", "Lehua", "Lokelani"));
  private static final String[] ALPHA = {"-A", "-B", "-C", "-D", "-E"};
  
  /**
   * Generates a complete listing of all valid location arguments to check arguments against.
   * 
   * @return SortedSet<String> locations - a collection of all possible valid location arguments.
   */
  public List<String> availableLounges() {
    
    List<String> locations = new ArrayList<String>();
    
    for (String tower : TOWERS) {
      
      locations.add(tower);
      for (String location : ALPHA) {
        locations.add(tower + location);   
      }
    }
    return locations;
  }
  
  /**
   * Checks to see if a string argument is contained in the list of all valid locations.
   * 
   * @param argument is the user submitted location [tower | lounge].
   * @return boolean value of whether or not the location is valid.
   */
  public boolean isValid (String argument) {
    List<String> validLocale = availableLounges();
    return validLocale.contains(argument);
  }
  
  /**
   * Default constructor for ValidateLocation class.
   * 
   */
  public ValidateLocation() {
    //empty constructor
  }
}
