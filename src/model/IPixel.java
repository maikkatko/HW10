package model;

/**
 * This interface represents a pixel and includes setters to change values.
 */
public interface IPixel extends IPixelState {
  /**
   * Sets the values of red.
   */
  void setR(int val);

  /**
   * Sets the values of green.
   */
  void setG(int val);

  /**
   * Sets the values of blue.
   */
  void setB(int val);

  /**
   * Sets the values of alpha.
   */
  void setA(int val);
}
