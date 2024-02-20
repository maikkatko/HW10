package controller.io;

import model.IImageState;

/**
 * This interface represents an image loader which can load various types of images.
 */
public interface IImageLoader {

  /**
   * Runs the image load method given a image file path.
   *
   * @param filename a String representing the path of the image to load
   * @return an IIMageState object representing the image to load
   */
  IImageState run(String filename);
}
