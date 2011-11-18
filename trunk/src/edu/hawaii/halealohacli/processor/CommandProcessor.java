package edu.hawaii.halealohacli.processor;

import javax.xml.datatype.DatatypeConfigurationException;
import edu.hawaii.halealohacli.command.Help;

/**
 * Processes the command given by the user
 * and executes then executes it.
 * @author mizumoto
 *
 */
public class CommandProcessor {
  
  private String userInput;
  
  /**
   * Constuctor method to access class level methods.
   * @param userInput Command given by the user.
   */
  public CommandProcessor(String userInput)  {
    this.userInput = userInput;
  }
  
  /**
   * Chooses module based on the command input.
   * @param command Command given by the user.
   */
  public void chooseModule(String command)  {
    if ("current-power".equals(command))  {
      System.out.println("currentPower");
    }
    else if ("daily-energy".equals(command))  {
      System.out.println("dailyEnergy");
    } 
    else if ("energy-since".equals(command))  {
      System.out.println("energySince");
    } 
    else if ("rank-towers".equals(command))  {
      System.out.println("rankTowers");
    } 
    else if ("help".equals(command))  {
      Help.run();
    } 
    else  {
      System.out.println("Not a valid command");
    }
  }
  
  
  /**
   * Runs all error checks on the command and executes it
   * if command is valid.
   * @throws DatatypeConfigurationException 
   */
  public void run() throws DatatypeConfigurationException  {
    CommandProcessor input = new CommandProcessor(this.userInput);
    String[] cmd = this.userInput.split(" ");
    input.chooseModule(cmd[0]);
    }
}
