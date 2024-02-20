package model;

/**
 * This class implements the IPixel interface and represents a single pixel. It has getters and
 * setters to get and set individual color channels of the pixel.
 */
public class PixelImpl implements IPixel {
  private int r;
  private int g;
  private int b;
  private int a;

  /**
   * This constructor takes the individual color channels of a pixel and inticializes thmem to
   * the PixelImpl object.
   *
   * @param r an int representing the red color channel
   * @param g an int representing the green color channel
   * @param b an int representing the blue color channel
   */
  public PixelImpl(int r, int g, int b, int a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
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
    } else if ( value > 255 ) {
      return 255;
    }

    return value;
  }

  @Override
  public void setR(int val) {
    this.r = clamp(val);
  }

  @Override
  public void setG(int val) {
    this.g = clamp(val);
  }

  @Override
  public void setB(int val) {
    this.b = clamp(val);
  }

  @Override
  public void setA(int val) {
    this.a = val;
  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public int getA() {
    return a;
  }

}
