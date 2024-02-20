package view;

import java.awt.image.BufferedImage;

/**
 * This interface represents some of the functionality in the GUI View that allows for getting
 * information from the controller.
 */
public interface IViewGUI {
  /**
   * Adds listeners to an array as subscribers to the emitters.
   *
   * @param listener an Array representing subscribers
   */
  void addViewListeners(ViewListener listener);

  /**
   * Set a string of text to a textbook.
   *
   * @param text a String representing any text to add to a textbook
   */
  void setViewText(String text);

  /**
   * Takes an errormessage and error title to generate a pop-up for the user to see.
   *
   * @param error a String representing an error message
   * @param errorTitle a String representing the title of an error
   */
  void errorHandler(String error, String errorTitle);

  /**
   * Sets an image to the canvas.
   *
   * @param image a BufferedImage object representing an image
   */
  void setImage(BufferedImage image);
}
