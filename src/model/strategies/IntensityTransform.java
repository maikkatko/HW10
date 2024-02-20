package model.strategies;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class implements the ITransformation interface for an intensity component greyscale
 * transformation. This involves taking an IImageState object, looping through the individual
 * IPixel objects stored within and generating a new IPixel object with all channels set to the
 * average value of all color channels for an intensity component greyscale. It then returns the
 * transformed image as a IImageState object.
 */
public class IntensityTransform implements ITransformation {
  /**
   * Takes a double and returns an int value representing the rounded value of the double
   * according to the following rules:
   * For decimal values >= 0.5, round up
   * For decimal values < 0.5, round down
   *
   * @param number a double representing the number to round
   * @return an int representing the rounded number
   */
  private int customRound(double number) {
    return (int) (number + 0.5);
  }

  @Override
  public IImageState apply(IImageState sourceImage) {
    if (sourceImage == null) {
      throw new IllegalStateException("image is null");
    }

    IImage newImage = new ImageImpl(sourceImage.getHeight(), sourceImage.getWidth());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        double r = sourceImage.getRedChannel(row, col);
        double g = sourceImage.getGreenChannel(row, col);
        double b = sourceImage.getBlueChannel(row, col);

        double average = (r + g + b) / 3;

        int roundedAve = customRound(average);

        int a = sourceImage.getAlpha(row, col);

        newImage.setPixel(row, col, roundedAve, roundedAve, roundedAve, a);
      }
    }

    return newImage;
  }
}
