package controller;

import java.awt.image.BufferedImage;

import model.IImageState;

/**
 * This class implements the run method in the IImageHandler interface for converting IImageState
 * objects to BufferedImage objects.
 */
public class ImageHandler implements IImageHandler {
  @Override
  public BufferedImage run(IImageState image) {
    BufferedImage imageFile = new BufferedImage(image.getWidth(),
            image.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        int r = image.getRedChannel(row, col);
        int g = image.getGreenChannel(row, col);
        int b = image.getBlueChannel(row, col);
        int a = image.getAlpha(row,col);

        int rgb = (a << 24) | (r << 16) | (g << 8) | b;

        imageFile.setRGB(col, row, rgb);
      }
    }

    return imageFile;
  }
}
