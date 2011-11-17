package edu.hawaii.halealohacli.command;

import org.wattdepot.client.WattDepotClientException;

/**
 * @author Ardell Klemme
 * 
 * Implements basic interface for Commands.
 *
 */
public interface Command {
  
  //Verifies that the commandName variable matches our current command class
  /**
   * Verifies the correct command name argument matches the class, and the correct number of args.
   * 
   * @param commandArguments - arguments to verify.
   * @return boolean value to see if command was called correctly.
   */
  boolean verify (String ... commandArguments);
  //runs the command with variable number of parameters
  /**
   * Basic run method that will run the command accepting list of strings as parameters.
   * 
   * @param parameters needed for running command.
   * @throws WattDepotClientException 
   */
  public void run (String ... parameters) throws WattDepotClientException;
}
