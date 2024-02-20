package controller;

import java.awt.image.BufferedImage;

import model.IImageState;

/**
 * This interface has a run method that allows for conversion from an IImageState object to a
 * BufferedImage object.
 */
public interface IImageHandler {
  /**
   * Converts an IImageState object to a BufferedImage object.
   *
   * @param image an IImageState object representing the image
   * @return a BufferedImage object representing an image
   */
  BufferedImage run(IImageState image);
}
