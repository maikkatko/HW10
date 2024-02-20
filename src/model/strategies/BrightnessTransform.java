package model.strategies;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This strategy takes an int brightenValue arg and adds it to the individual color channels of
 * every pixel in a given image.
 */
public class BrightnessTransform implements ITransformation {
  private final int brightenValue;

  /**
   * This constructor takes an int value and initializes the field to it.
   *
   * @param brightenValue an int representing the value to brighten or darken the image with
   */
  public BrightnessTransform(int brightenValue) {
    this.brightenValue = brightenValue;
  }

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

  @Override
  public IImageState apply(IImageState sourceImage) {
    if (sourceImage == null) {
      throw new IllegalStateException("image is null");
    }

    IImage newImage = new ImageImpl(sourceImage.getHeight(), sourceImage.getWidth());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = clamp(sourceImage.getRedChannel(row, col) + brightenValue);
        int g = clamp(sourceImage.getGreenChannel(row, col) + brightenValue);
        int b = clamp(sourceImage.getBlueChannel(row, col) + brightenValue);
        int a = sourceImage.getAlpha(row, col);
        newImage.setPixel(row, col, r, g, b, a);
      }
    }

    return newImage;
  }
}
