package controller.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class loads various types of images to an IImageState object and adds it to the image
 * database.
 */
public class ImageLoader implements IImageLoader {
  @Override
  public IImageState run(String filename) {
    IImage image;
    BufferedImage img = null;

    try {
      img = ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new IllegalStateException("Could not read image");
    }

    image = new ImageImpl(img.getHeight(), img.getWidth());


    for (int row = 0; row < img.getHeight(); row++) {
      for (int col = 0; col < img.getWidth(); col++) {
        int pixel = img.getRGB(col, row);
        Color color = new Color(pixel);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        image.setPixel(row, col, r, g, b, a);
      }
    }

    return image;
  }
}
