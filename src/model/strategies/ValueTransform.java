package model.strategies;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class implements the ITransformation interface for a value component greyscale
 * transformation. This involves taking an IImageState object, looping through the individual
 * IPixel objects stored within and generating a new IPixel object with all channels set to the
 * max value of all color channels for a value component greyscale. It then returns the
 * transformed image as a IImageState object.
 */
public class ValueTransform implements ITransformation {

  /**
   * Takes an int value and checks if it is < 0 or > 255 and clamps it to either one.
   *
   * @param value an int representing the value to clamp
   * @return an int representing the clamped value
   */
  private int clamp(int value) {
    if ( value < 0 ) {
      return 0;
    }
    if ( value > 255 ) {
      return 255;
    }

    return value;
  }

  /**
   * takes in individual color channel values as ints and gets the max.
   *
   * @param r an int representing the red channel
   * @param g an int representing the green channel
   * @param b an int representing the blue channel
   * @return an int representing the max value
   */
  private int findMax(int r, int g, int b) {
    return Math.max(r, Math.max(g, b));
  }

  @Override
  public IImageState apply(IImageState sourceImage) {
    if (sourceImage == null) {
      throw new IllegalStateException("image is null");
    }

    IImage newImage = new ImageImpl(sourceImage.getHeight(), sourceImage.getWidth());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = sourceImage.getRedChannel(row, col);
        int g = sourceImage.getGreenChannel(row, col);
        int b = sourceImage.getBlueChannel(row, col);
        int a = sourceImage.getAlpha(row, col);

        int max = clamp(findMax(r, g, b));

        newImage.setPixel(row, col, max, max, max, a);
      }
    }

    return newImage;
  }
}
