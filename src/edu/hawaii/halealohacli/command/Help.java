package edu.hawaii.halealohacli.command;

/**
 * Provides the list of available commands and
 * their parameters.
 * @author Matthew Mizumoto
 *
 */
public class Help {
  
  
  /**
   * Runs the program and
   * prints out the options for this application.
   */
  public static void run()  {
    System.out.println("Here are the available commands for this system.");
    System.out.println("current-power [tower | lounge]");
    System.out.println("Returns the current power in kW for the associated tower or lounge.");
    System.out.println("daily-energy [tower | lounge] [date]");
    System.out.println("Returns the energy in kWh used by the tower or lounge " +
                       "for the specified date (yyyy-mm-dd).");
    System.out.println("energy-since [tower | lounge] [date]");
    System.out.println("Returns the energy used since the date (yyyy-mm-dd) to now.");
    System.out.println("rank-towers [start] [end]");
    System.out.println("Returns a list in sorted order from least to most energy consumed" +
                       " between the [start] and [end] date (yyyy-mm-dd)");
    System.out.println("quit");
    System.out.println("Terminates execution.");
    System.out.println("Note: towers are:  Mokihana, Ilima, Lehua, Lokelani");
    System.out.println("Lounges are the tower names followed by a \"-\" " +
                       "followed by one of A, B, C, D, E. For example, Mokihana-A.");
  }

}
