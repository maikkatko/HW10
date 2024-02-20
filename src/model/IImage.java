package model;

/**
 * This interface extends the IImageState interface and adds a pixel setter method for setting
 * custom pixels to an image. This seperates the setters from the getters so that access to
 * setters can be restricted.
 */
public interface IImage extends IImageState {
  /**
   * Sets the color channels in an IPixel object at a row-col position in an IImage object.
   *
   * @param row an int representing the row position in an IImage objects IPixel[][] array
   * @param col an int representing the col position in an IImage objects IPixel[][] array
   * @param r an int representing the red channel
   * @param g an int representing the green channel
   * @param b an int representing the blue channel
   * @param a an int representing the alpha transparency value
   */
  void setPixel(int row, int col, int r, int g, int b, int a);
}
