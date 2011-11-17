package edu.hawaii.halealohacli.command;

/**
 * @author Ardell Klemme
 * 
 * Implements basic interface for Commands.
 *
 */
public interface Command {
  
  //verfies that the commandName variable matches our current command class
  boolean verify (String commandName, String ... commandArguments);
  //runs the command with variable number of parameters
  public void run (String ... parameters);
}
