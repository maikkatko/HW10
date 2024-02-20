package controller.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import model.IImageState;
import model.IPixelState;
import view.IView;

/**
 * This class implements the IImageSaver interface for a PPM image format, implementing the run
 * method with functionality specific to saving PPM formatted images.
 */
public class PPMImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;
  private IView view;

  /**
   * This constructor initializes an image path and an image object for saving.
   *
   * @param pathToSave a String representing the image path
   * @param image an IImageState object representing the image to save
   */
  public PPMImageSaver(String pathToSave, IImageState image) {
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
  public PPMImageSaver(String pathToSave, IImageState image, IView view) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void run() {
    int width = this.image.getWidth();
    int height = this.image.getHeight();
    IPixelState[][] data = this.image.getData();

    try (FileOutputStream fos = new FileOutputStream(pathToSave)) {
      fos.write("P3\n".getBytes());

      fos.write((width + " " + height + "\n").getBytes());

      fos.write("255\n".getBytes());

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = data[i][j].getR();
          int g = data[i][j].getG();
          int b = data[i][j].getB();

          if ( j == width - 1 ) {
            this.view.renderMessage(r + " " + g + " " + b);
            fos.write((r + "\n").getBytes());
            fos.write((g + "\n").getBytes());
            fos.write((b + "\n").getBytes());
            continue;
          } else if (i == height - 1 && j == width - 1) {
            fos.write((r + "\n").getBytes());
            fos.write((g + "\n").getBytes());
            fos.write(Integer.toString(b).getBytes());
            return;
          }

          this.view.renderMessage(r + " " + g + " " + b + " ");
          fos.write((r + "\n").getBytes());
          fos.write((g + "\n").getBytes());
          fos.write((b + "\n").getBytes());
        }
        this.view.renderMessage("\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Invalid state");
    }
  }
}

/*
  12 12 12 14 14 14
  23 23 23 10 10 10`
 */