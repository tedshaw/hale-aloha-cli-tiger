package edu.hawaii.halealohacli.command;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A class for sorting Data by energy consumption.
 * 
 * @author Terrence Chida
 * 
 */
public class SortByEnergy implements Comparator<Data>, Serializable {

  private static final long serialVersionUID = 8844940986910712936L;

  /**
   * Defines the comparison of Data.
   * 
   * @param d1
   *          A Data object.
   * @param d2
   *          A Data object.
   * @return an int representing the order of d2 in comparison to d1.
   */
  public int compare(Data d1, Data d2) {
    if (d1.getEnergy() == d2.getEnergy()) {
      return 0;
    }
    else if (d1.getEnergy() < d2.getEnergy()) {
      return -1;
    }
    else {
      return 1;
    }
  } // End compare()
} // End SortByEnergy
