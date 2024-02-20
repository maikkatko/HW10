package view;

import java.io.IOException;

import model.IImageState;
import model.IModel;
import model.IPixelState;

/**
 * This calls represents the implementation of the view interface and defines the fields and
 * methods necessary to record and transmit information to the user.
 */
public class ViewImpl implements IView {
  private IModel model;
  private Appendable log;

  /**
   * A constructor that initializes the model.
   *
   * @param model an IModel object representing the model
   */
  public ViewImpl(IModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have null for model");
    }

    this.model = model;
  }

  /**
   * This constructor takes an IModel and an Appendable object representing a log to append
   * information to.
   *
   * @param model an IModel object representing the model
   * @param log an Appendable object representing the log
   */
  public ViewImpl(IModel model, Appendable log) {
    if (model == null || log == null) {
      throw new IllegalArgumentException("Cannot have null");
    }

    this.model = model;
    this.log = log;
  }

  @Override
  public String toString(String idImage) {
    StringBuilder pixels = new StringBuilder();
    IImageState image = this.model.get(idImage);



    if (image == null) {
      throw new IllegalStateException("image is null");
    }

    IPixelState[][] data = image.getData();

    int height = data.length;
    int width = data[0].length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = data[i][j].getR();
        int g = data[i][j].getG();
        int b = data[i][j].getB();

        pixels.append("Colors at Pixel ("
                      + i + ", " + j + "): "
                      + r + ", " + g + ", " + b + "\n");
      }
    }

    return pixels.toString();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.log.append(message + "\n");
  }

  @Override
  public String getMessage() {
    return this.log.toString();
  }
}
