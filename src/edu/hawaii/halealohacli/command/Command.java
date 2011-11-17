package edu.hawaii.halealohacli.command;

import org.wattdepot.client.WattDepotClient;

/**
 * @author Ardell Klemme
 * 
 * Implements basic interface for Commands.
 *
 */
public interface Command {
  
  /**
   * Basic run method that will run the command accepting list of strings as parameters.
   * 
   * @param client is the WattDepotClient passed from the Main class.
   * @param parameters needed for running command.
   * @throws Exception this is a catch-all for exceptions.  We'll generate more detailed exceptions
   * at a later date. 
   */
  public void run (WattDepotClient client, String ... parameters) throws Exception;
}
