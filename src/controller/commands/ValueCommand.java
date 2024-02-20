package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.ITransformation;
import model.strategies.ValueTransform;
import view.IView;

/**
 * This class implements the ICommand interface for a value component greyscale transformation.
 * This involves parsing a scanner for the data needed and then calling the valueTransform strategy.
 */
public class ValueCommand implements ICommand {
  @Override
  public void run(Scanner scanner, IModel model, IView view) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid input or no more input");
    }

    String sourceId = scanner.next();

    IImageState sourceImage = model.get(sourceId);

    if ( sourceImage == null) {
      throw new IllegalStateException("image doesn't exist");
    }

    if (scanner.hasNextInt() || !scanner.hasNext()) {
      throw new IllegalStateException("Invalid input or no more input");
    }

    String destId = scanner.next();

    ITransformation valueTransform = new ValueTransform();

    IImageState valueImage = valueTransform.apply(sourceImage);

    model.add(destId, valueImage);
  }
}
