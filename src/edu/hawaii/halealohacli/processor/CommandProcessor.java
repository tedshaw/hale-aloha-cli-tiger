package edu.hawaii.halealohacli.processor;

import org.wattdepot.client.WattDepotClient;
import edu.hawaii.halealohacli.command.CurrentPower;
import edu.hawaii.halealohacli.command.DailyEnergy;
import edu.hawaii.halealohacli.command.EnergySince;
import edu.hawaii.halealohacli.command.Help;
import edu.hawaii.halealohacli.command.RankTowers;

/**
 * Processes the command given by the user and executes then executes it.
 * 
 * @author Matthew Mizumoto
 * 
 */
public class CommandProcessor {

  private String userInput;
  private WattDepotClient client;

  /**
   * Constuctor method to access class level methods.
   * 
   * @param client The WattDepot client.
   * @param userInput Command given by the user.
   */
  public CommandProcessor(WattDepotClient client, String userInput) {
    this.userInput = userInput;
    this.client = client;
  }

  /**
   * Chooses module based on the command input.
   * 
   * @param command Command given by the user.
   * @throws Exception if problem connecting to WattDepot Server.
   */
  public void chooseModule(String command) throws Exception {
    if ("current-power".equals(command)) {
      CurrentPower currentPower = new CurrentPower(client);
      currentPower.run(userInput);
    }
    else if ("daily-energy".equals(command)) {
      DailyEnergy dailyEnergy = new DailyEnergy(client);
      dailyEnergy.run(userInput);
    }
    else if ("energy-since".equals(command)) {
      EnergySince energySince = new EnergySince(client);
      energySince.run(userInput);
    }
    else if ("rank-towers".equals(command)) {
      RankTowers rankTowers = new RankTowers(client);
      rankTowers.run(userInput);
    }
    else if ("help".equals(command)) {
      Help.run();
    }
    else {
      System.out.println("Not a valid command");
    }
  }

  /**
   * Runs all error checks on the command and executes it if command is valid.
   * 
   * @throws Exception If cannot connect to WattDepot Client.
   */
  public void run() throws Exception {
    CommandProcessor input = new CommandProcessor(client, this.userInput);
    String[] cmd = this.userInput.split(" ");
    input.chooseModule(cmd[0]);
  }
}
