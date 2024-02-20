package model.strategies;

import model.IImageState;

/**
 * This interface represents the strategies necessary to apply any changes or transformations to
 * a given image. Strategies include but are not limited to:
 * Brightness - Brighten or darken a given image
 * Red/Green/Blue - Greyscale of an image based on individual color channels
 * Value/Intensity/Luma - Greyscale of an image based on either value, intensity, or luma
 */
public interface ITransformation {

  /**
   * Applies a specific transformation stately to the image and returns a transformed copy.
   *
   * @param image an IImageState object representing the source image to transform
   * @return an IImageState object representing the copy of the transformed image
   */
  IImageState apply(IImageState image);
}
