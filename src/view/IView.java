package view;

import java.io.IOException;

/**
 * This interface represents the view of this project, allowing for appending messages to a log
 * and outputting information to the user.
 */
public interface IView {
  /**
   * Generates a string representation for the 2D array of pixels for testing.
   *
   * @return a String representing the array of pixels
   */
  String toString(String idImage);

  /**
   * Appends a given message to a log appendable.
   *
   * @param message a String representing the message to append.
   * @throws IOException in case an invalid append is made
   */
  void renderMessage(String message) throws IOException;

  /**
   * This returns the contents of the log as a string for testing.
   *
   * @return a String representing the message
   */
  String getMessage();
}
