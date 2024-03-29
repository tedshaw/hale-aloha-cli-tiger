package edu.hawaii.halealohacli.processor;

import org.wattdepot.client.WattDepotClient;
import edu.hawaii.halealohacli.command.CurrentPower;
import edu.hawaii.halealohacli.command.DailyEnergy;
import edu.hawaii.halealohacli.command.EnergySince;
import edu.hawaii.halealohacli.command.Help;
import edu.hawaii.halealohacli.command.RankTowers;
import edu.hawaii.halealohacli.command.SetBaseline;
import edu.hawaii.halealohacli.command.MonitorPower;
import edu.hawaii.halealohacli.command.MonitorGoal;

/**
 * Processes the command given by the user and executes then executes it.
 * 
 * @author Matthew Mizumoto
 * 
 */
public class CommandProcessor {
  private CurrentPower currentPower;
  private DailyEnergy dailyEnergy;
  private EnergySince energySince;
  private RankTowers rankTowers;
  private SetBaseline setBaseline;
  private MonitorPower monitorPower;
  private MonitorGoal monitorGoal;

  /**
   * Constuctor method to access class level methods.
   * 
   * @param client The WattDepot client.
   */
  public CommandProcessor(WattDepotClient client) {
    currentPower = new CurrentPower(client);
    dailyEnergy = new DailyEnergy(client);
    energySince = new EnergySince(client);
    rankTowers = new RankTowers(client);
    setBaseline = new SetBaseline(client);
    monitorPower = new MonitorPower(client);
    monitorGoal = new MonitorGoal(client);
  }

  /**
   * Runs all error checks on the command and executes it if command is valid.
   * 
   * @param userInput Command given by the user.
   * @throws Exception If cannot connect to WattDepot Client.
   */
  public void run(String userInput) throws Exception {
    String[] cmd = userInput.split(" ");
    String command = cmd[0];
    if ("current-power".equals(command)) {
      currentPower.run(userInput);
    }
    else if ("daily-energy".equals(command)) {
      dailyEnergy.run(userInput);
    }
    else if ("energy-since".equals(command)) {
      energySince.run(userInput);
    }
    else if ("rank-towers".equals(command)) {
      rankTowers.run(userInput);
    }
    else if ("set-baseline".equals(command)) {
      setBaseline.run(userInput);
    }
    else if ("monitor-power".equals(command)) {
      monitorPower.run(userInput);
    }
    else if ("monitor-goal".equals(command)) {
      monitorGoal.run(userInput);
    }
    else if ("help".equals(command)) {
      Help.run();
    }
    else {
      System.out.println("Not a valid command");
    }
  }
}
