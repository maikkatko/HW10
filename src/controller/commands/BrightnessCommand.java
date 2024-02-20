package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.BrightnessTransform;
import model.strategies.ITransformation;
import view.IView;

/**
 * This class implements the ICommand interface for a brightening or darkening transformation.
 * This involves parsing a scanner for the data needed and then calling the brightenTransform
 * strategy.
 */
public class BrightnessCommand implements ICommand {
  @Override
  public void run(Scanner scanner, IModel model, IView view) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);

    if (!scanner.hasNextInt()) {
      throw new IllegalStateException("Second arg must be int");
    }

    int value = scanner.nextInt();
    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid input or no more input");
    }
    String sourceId = scanner.next();

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid input or no more input");
    }
    String destId = scanner.next();

    IImageState sourceImage = model.get(sourceId);
    if ( sourceImage == null) {
      throw new IllegalStateException("image doesn't exist");
    }

    ITransformation brightenTransform = new BrightnessTransform(value);

    IImageState brightenedImage = brightenTransform.apply(sourceImage);

    model.add(destId, brightenedImage);
  }
}
