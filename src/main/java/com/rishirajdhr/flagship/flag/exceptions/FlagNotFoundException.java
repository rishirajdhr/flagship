package com.rishirajdhr.flagship.flag.exceptions;

/**
 * Represents an exception when a requested flag is not found.
 */
public class FlagNotFoundException extends RuntimeException {
  /**
   * Create an exception when no flag with a given name exists.
   *
   * @param name the name for which no flag exists
   */
  public FlagNotFoundException(String name) {
    super("No flag found in project with name: " + name);
  }

  /**
   * Create an exception when no flag with a given ID exists.
   *
   * @param id the ID for which no flag exists
   */
  public FlagNotFoundException(Long id) {
    super("No flag found in project with ID: " + id);
  }
}
