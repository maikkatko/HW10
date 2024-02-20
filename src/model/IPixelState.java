package model;

/**
 * This interface defines the RGB components of an individual pixel along with getters to access
 * those components.
 */
public interface IPixelState {
  /**
   * Gets the value of red.
   *
   * @return an int representing the value of red
   */
  int getR();

  /**
   * Gets the value of blue.
   *
   * @return an int representing the value of green
   */
  int getG();

  /**
   * Gets the value of green.
   *
   * @return an int representing the value of blue
   */
  int getB();

  /**
   * Gets the value of alpha.
   *
   * @return an int representing the value of alpha
   */
  int getA();
}
