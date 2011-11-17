package edu.hawaii.halealohacli.command;

import org.wattdepot.client.WattDepotClient;
import org.wattdepot.client.WattDepotClientException;

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
   * @param parameters needed for running command.
   * @throws WattDepotClientException 
   */
  public void run (WattDepotClient client, String ... parameters) throws WattDepotClientException;
}
