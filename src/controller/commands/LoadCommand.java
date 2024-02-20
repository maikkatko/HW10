package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageLoader;
import controller.io.ImageLoader;
import controller.io.PPMImageLoader;
import model.IImageState;
import model.IModel;
import view.IView;

/**
 * This class implements the ICommand interface for a load command to load an image into the
 * database.
 */
public class LoadCommand implements ICommand {
  @Override
  public void run(Scanner scanner, IModel model, IView view) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid filename");
    }

    String filename = scanner.next();
    if (!filename.contains(".")) {
      throw new IllegalStateException("Invalid filename");
    }

    String filetype = filename.split("\\.")[1];

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid image id");
    }

    String idImage = scanner.next();

    if (filetype.equalsIgnoreCase("ppm")) {
      IImageLoader loader = new PPMImageLoader();
      IImageState newImage = loader.run(filename);
      model.add(idImage, newImage);
      return;
    } else if (filetype.equalsIgnoreCase("jpg")) {
      // In case extension is input as "jpg" instead of "jpeg" for ImageIO.read
      filetype = "jpeg";
      String path = filename.split("\\.")[0];
      filename = path + "." + filetype;
    }

    IImageLoader loader = new ImageLoader();
    IImageState newImage = loader.run(filename);
    model.add(idImage, newImage);
  }
}
