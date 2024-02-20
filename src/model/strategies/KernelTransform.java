package model.strategies;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class applies a kernel transform based on a given kernel using a sliding window technique
 * to apply the transform pixel by pixel.
 */
public class KernelTransform implements ITransformation {
  private double[][] kernel;

  public KernelTransform(double[][] kernel) {
    this.kernel = kernel;
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

  /**
   * Checks if given coordinates are within the dimensions of the given image.
   *
   * @param image an IImageState object representing the image
   * @param y an int representing the y coordinate
   * @param x an int representing the x coordinate
   * @return a boolean true if coordinates are valid, false otherwise
   */
  private boolean isValid(IImageState image, int y, int x) {
    return y >= 0
            && y < image.getHeight()
            && x >= 0
            && x < image.getWidth();
  }

  @Override
  public IImageState apply(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getHeight(), sourceImage.getWidth());
    int radius = kernel.length / 2;


    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        double rSum = 0;
        double gSum = 0;
        double bSum = 0;
        int a = sourceImage.getAlpha(row, col);

        for (int ky = -radius; ky <= radius; ky++) {
          for (int kx = -radius; kx <= radius; kx++) {
            if (isValid(sourceImage, row + ky, col + kx)) {
              int r = sourceImage.getRedChannel(row + ky, col + kx);
              int g = sourceImage.getGreenChannel(row + ky, col + kx);
              int b = sourceImage.getBlueChannel(row + ky, col + kx);

              double weight = this.kernel[ky + radius][kx + radius];

              rSum += r * weight;
              gSum += g * weight;
              bSum += b * weight;
            }
          }
        }

        int newRed = clamp(customRound(rSum));
        int newGreen = clamp(customRound(gSum));
        int newBlue = clamp(customRound(bSum));
        newImage.setPixel(row, col, newRed, newGreen, newBlue, a);
      }
    }

    return newImage;
  }
}
