package edu.hawaii.halealohacli;

import java.util.Scanner;
import org.wattdepot.client.WattDepotClient;
import edu.hawaii.halealohacli.processor.CommandProcessor;

/**
 * Main class that prompts the user for commands.
 * 
 * @author Matthew Mizumoto
 */
public class Main {

  /**
   * 
   * @param args no arguments required
   * @throws Exception if cannot make connection to server
   */
  public static void main(String[] args) throws Exception {
    CommandProcessor command;
    String url = "http://server.wattdepot.org:8190/wattdepot/";
    // Create a client.
    WattDepotClient client = new WattDepotClient(url);
    // Check to make sure a connection can be made.
    // If no connection, then exit right now.
    if (client.isHealthy()) {
      System.out.println("Successfully connected to the Hale Aloha Wattdepot Server");
    }
    else {
      System.out.format("Could not connect to: %s%n", url);
      return;
    }
    command = new CommandProcessor(client);

    System.out.format("%s", "> ");
    Scanner keybd = new Scanner(System.in);
    String input = keybd.nextLine();
    while (!("quit".equals(input))) {
      command.run(input);
      System.out.format("%s", "> ");
      input = keybd.nextLine();
    }
    
  }

}
