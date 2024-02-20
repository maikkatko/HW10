package model.strategies;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class applies a filter transform based on a given matrix using matrix multiplication with
 * the individual RGB values of each pixel.
 */
public class FilterTransform implements ITransformation {
  private final double[][] matrix;

  public FilterTransform(double[][] matrix) {
    this.matrix = matrix;
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
        int r = sourceImage.getRedChannel(row, col);
        int g = sourceImage.getGreenChannel(row, col);
        int b = sourceImage.getBlueChannel(row, col);
        int a = sourceImage.getAlpha(row, col);

        double[] colorVector = {r, g, b};
        double[] transformedVector = new double[3];

        for (int i = 0; i < 3; i++) {
          for (int k = 0; k < 3; k++) {
            transformedVector[i] += this.matrix[i][k] * colorVector[k];
          }
        }

        int newR = clamp(customRound(transformedVector[0]));
        int newG = clamp(customRound(transformedVector[1]));
        int newB = clamp(customRound(transformedVector[2]));

        newImage.setPixel(row, col, newR, newG, newB, a);
      }
    }

    return newImage;
  }
}

