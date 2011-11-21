package edu.hawaii.halealohacli.command;

/**
 * @author Ardell Klemme
 * 
 * Implements basic interface for Commands.
 * 
 */
public interface Command {

  /**
   * Basic validation method thats tests to see the correct parameters were entered.
   * @param command the command to be tested
   * @return true if the command's parameters are correct.
   */
  public boolean isValid(String command);

  /**
   * Basic run method that will run the command accepting list of strings as parameters.
   * 
   * @param command command needed to run the program
   * @throws Exception this is a catch-all for exceptions. We'll generate more detailed exceptions
   * at a later date.
   */
  public void run(String command) throws Exception;
}