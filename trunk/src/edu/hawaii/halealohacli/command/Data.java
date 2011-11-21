package edu.hawaii.halealohacli.command;

/**
 * Provides the Data class.
 * 
 * @author Terrence Chida
 * 
 */
public class Data {
  private String sourceName;

  private long latency;

  private int energy;
  
  private int power;


  /**
   * Main constructor.
   * 
   * @param source
   *          The name of the source.
   * @param seconds
   *          The latency of the data in seconds.
   * @param energy
   *          The energy consumption in watt-hours.
   * @param power
   *          The power consumption in watts.
   */
  public Data(String source, long seconds, int energy, int power) {
    this.sourceName = source;
    this.latency = seconds;
    this.energy = energy;
    this.power = power;
  }

  /**
   * Constructor for when source and latency are provided.
   * 
   * @param source
   *          The name of the source.
   * @param seconds
   *          The latency of the data in seconds.
   */
  public Data(String source, long seconds) {
    this(source, seconds, 0, 0);
  }

  /**
   * Constructor for when source and energy are provided.
   * 
   * @param source
   *          The name of the source.
   * @param energy
   *          The energy consumption in watt-hours.
   */
  public Data(String source, int energy) {
    this(source, 0, energy, 0);
  }
  
  /**
   * Constructor for when source and power are provided.
   * 
   * @param source
   *          The name of the source.
   * @param energy
   *          The energy consumption in watt-hours.
   * @param power 
   *          The power consumption in watts.
   */
  public Data(String source, int energy, int power) {
    this(source, 0, 0, power);
  }

  /**
   * Accessor for sourceName.
   * 
   * @return The name of the source.
   */
  public String getSource() {
    return this.sourceName;
  }

  /**
   * Accessor for latency.
   * 
   * @return The latency in seconds.
   */
  public long getLatency() {
    return this.latency;
  }

  /**
   * Accessor for energy.
   * 
   * @return The energy consumption in watt-hours.
   */
  public int getEnergy() {
    return this.energy;
  }
  
  /**
   * Accessor for power.
   * 
   * @return The power consumption in watts.
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Constructs a formatted string of the source name and latency.
   * @return A string of the source name and latency.
   */
  @Override
  public String toString() {
    if (this.latency >= 0) {
      return String.format("%20s      %s%n", sourceName, latency);
    }
    else {
      return String.format("%20s      %s%n", sourceName, "No data received.");
    }
  }

}