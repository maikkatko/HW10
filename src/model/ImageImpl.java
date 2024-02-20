package model;

/**
 * This class represents a model and implements various methods to be used on a set of images.
 */
public class ImageImpl implements IImage {
  private final IPixelState[][] data;
  private int height;
  private int width;

  /**
   * This constructor initializes an empty image with a given height and width.
   *
   * @param height an int representing the height of the image
   * @param width an int representing the width of the image
   */
  public ImageImpl(int height, int width) {
    this.height = height;
    this.width = width;
    this.data = new PixelImpl[height][width];
  }

  /**
   * Takes an int value and checks if it is < 0 or > 255 and clamps it to either one.
   *
   * @param value an int representing the value to clamp
   * @return an int representing the clamped value
   */
  protected int clamp(int value) {
    if ( value < 0 ) {
      return 0;
    } else if ( value > 255 ) {
      return 255;
    }

    return value;
  }

  @Override
  public void setPixel(int row, int col, int r, int g, int b, int a) {
    this.data[row][col] = new PixelImpl(clamp(r), clamp(g), clamp(b), a);
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getRedChannel(int row, int col) {
    return this.data[row][col].getR();
  }

  @Override
  public int getGreenChannel(int row, int col) {
    return this.data[row][col].getG();
  }

  @Override
  public int getBlueChannel(int row, int col) {
    return this.data[row][col].getB();
  }

  @Override
  public int getAlpha(int row, int col) {
    return this.data[row][col].getA();
  }

  @Override
  public IPixelState[][] getData() {
    return this.data;
  }
}
