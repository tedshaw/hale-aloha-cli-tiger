package edu.hawaii.halealohacli.processor;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Processes the command given by the user
 * and executes then executes it.
 * @author mizumoto
 *
 */
public class CommandProcessor {
  
  private static List<String> commands = new ArrayList<String>();
  private String userInput;
  
  /**
   * Constuctor method to access class level methods.
   * @param userInput Command given by the user.
   */
  public CommandProcessor(String userInput)  {
    this.userInput = userInput;
  }

  /**
   * Adds the valid commands in an ArrayList.
   * This also allows for more inputs to be added later
   */
  public static void setCommands()  {
    commands.clear();
    commands.add("current-power");
    commands.add("daily-energy");
    commands.add("energy-since");
    commands.add("rank-towers");
    commands.add("help");
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
      System.out.println("help");
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
    setCommands();
    input.chooseModule(cmd[0]);
    }
}
