package model;

/**
 * This interface represents an image via an IPixel array, with getters for various parameters of
 * the array.
 */
public interface IImageState {
  /**
   * Gets the IPixel array.
   *
   * @return an IPixelState[][] object representing the image array
   */
  IPixelState[][] getData();

  /**
   * Gets the width of the IPixel array.
   *
   * @return an int representing the width of the IPixel array
   */
  int getWidth();

  /**
   * Gets the height of the IPixel array.
   *
   * @return an int representing the height of the IPixel array
   */
  int getHeight();

  /**
   * Gets the value of the red color channel at a given row and col in the IPixel array.
   *
   * @param row an int representing the row position in the IPixel array
   * @param col an int representing the col position in the IPixel array
   * @return an int representing the red color channel
   */
  int getRedChannel(int row, int col);

  /**
   * Gets the value of the green color channel at a given row and col in the IPixel array.
   *
   * @param row an int representing the row position in the IPixel array
   * @param col an int representing the col position in the IPixel array
   * @return an int representing the green color channel
   */
  int getGreenChannel(int row, int col);

  /**
   * Gets the value of the blue color channel at a given row and col in the IPixel array.
   *
   * @param row an int representing the row position in the IPixel array
   * @param col an int representing the col position in the IPixel array
   * @return an int representing the blue color channel
   */
  int getBlueChannel(int row, int col);

  /**
   * Gets the value of alpha at a given row and col in the IPixel array.
   *
   * @param row an int representing the row position in the IPixel array
   * @param col an int representing the col position in the IPixel array
   * @return an int representing alpha
   */
  int getAlpha(int row, int col);
}
