package controller.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class implements the IImageLoader interface for a PPM image format, implementing the run
 * method with functionality specific to loading PPM formatted images.
 */
public class PPMImageLoader implements IImageLoader {

  /**
   * Takes an int value and checks if it is < 0 or > 255 and clamps it to either one.
   *
   * @param value an int representing the value to clamp
   * @return an int representing the clamped value
   */
  private int clamp(int value) {
    if (value < 0) {
      return 0;
    } else if ( value > 255 ) {
      return 255;
    }

    return value;
  }

  @Override
  public IImageState run(String filename) {
    Scanner sc;
    IImage image;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      try {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      } catch (StringIndexOutOfBoundsException e) {
        throw new IllegalStateException("Invalid file format or file corrupted");
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("File must be a PPM");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    image = new ImageImpl(height, width);

    int maxValue = sc.nextInt();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = clamp(sc.nextInt());
        int g = clamp(sc.nextInt());
        int b = clamp(sc.nextInt());
        int a = 0;

        image.setPixel(row, col, r, g, b, a);
      }
    }

    return image;
  }
}
