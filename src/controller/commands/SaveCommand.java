package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageSaver;
import controller.io.ImageSaver;
import controller.io.PPMImageSaver;
import model.IModel;
import view.IView;

/**
 * This class implements the ICommand interface for a save command which saves an image to the
 * a directory within the project. This involves parsing a scanner for the data needed and then
 * calling a saver command.
 */
public class SaveCommand implements ICommand {
  @Override
  public void run(Scanner scanner, IModel model, IView view) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    Appendable log = new StringBuilder();

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid filename or no more input");
    }

    String filename = scanner.next();
    if (!filename.contains(".")) {
      throw new IllegalStateException("Invalid filename");
    }

    String filetype = (filename.split("\\.")[1].toLowerCase());

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid image id or no more input");
    }

    String idImage = scanner.next();

    if (filetype.equals("ppm")) {
      IImageSaver saverPPM = new PPMImageSaver(filename, model.get(idImage), view);
      saverPPM.run();
      return;
    }

    IImageSaver saver = new ImageSaver(filename, model.get(idImage), view);
    saver.run();
  }
}
