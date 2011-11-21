package edu.hawaii.halealohacli.command;

/**
 * Checks to see if a date matches the form 2011-MM-DD.
 * 
 * @author Terrence Chida
 */
public class VerifyDate {

  private static final String regex = "^2011-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

  /**
   * Matches the input against a regular expression.
   * 
   * @param date
   *          The date to be checked.
   * @return True if the date is valid, and false otherwise.
   */
  public static boolean isValidDate(String date) {
    if (date.matches(regex)) {
      return true;
    }
    return false;
  } // end isValidDate()
} // end VerifyDate
