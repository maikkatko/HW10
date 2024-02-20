package controller.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageState;
import view.IView;

/**
 * This class saves various types of images to a given filepath.
 */
public class ImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;
  private IView view;

  /**
   * This constructor initializes an image path and an image object for saving.
   *
   * @param pathToSave a String representing the image path
   * @param image an IImageState object representing the image to save
   */
  public ImageSaver(String pathToSave, IImageState image) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
  }

  /**
   * This constructor initializes an image path and an image object for saving, along with an
   * Appendable log for testing the saver functionality.
   *
   * @param pathToSave a String representing the image path
   * @param image an IImageState object representing the image to save
   * @param view an IView object representing the view
   */
  public ImageSaver(String pathToSave, IImageState image, IView view) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void run() {
    BufferedImage imageFile = new BufferedImage(this.image.getWidth(),
            this.image.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        int r = image.getRedChannel(row, col);
        int g = image.getGreenChannel(row, col);
        int b = image.getBlueChannel(row, col);
        int a = image.getAlpha(row,col);

        int rgb = (a << 24) | (r << 16) | (g << 8) | b;

        if ( col == image.getWidth() - 1 ) {
          try {
            this.view.renderMessage(r + " " + g + " " + b);
            imageFile.setRGB(col, row, rgb);
            continue;
          } catch (IOException e) {
            throw new IllegalStateException("Cannot write to view");
          }
        } else if (row == image.getHeight() - 1 && col == image.getWidth() - 1) {
          imageFile.setRGB(col, row, rgb);
          return;
        }

        try {
          this.view.renderMessage(r + " " + g + " " + b + " ");
        } catch (IOException e) {
          throw new IllegalStateException("Cannot write to view");
        }
        imageFile.setRGB(col, row, rgb);
      }

      try {
        this.view.renderMessage("\n");
      } catch (IOException e) {
        throw new IllegalStateException("Cannot write to view");
      }
    }

    String filetype = this.pathToSave.split("\\.")[1];

    try {
      ImageIO.write(imageFile, filetype, new File(pathToSave));
    } catch (IOException e) {
      throw new IllegalStateException("Could not write image to file");
    }
  }
}

