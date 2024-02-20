package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.ITransformation;
import model.strategies.IntensityTransform;
import view.IView;

/**
 * This class implements the ICommand interface for an intensity component greyscale transformation.
 * This involves parsing a scanner for the data needed and then calling the intensityTransform
 * strategy.
 */
public class IntensityCommand implements ICommand {
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

    ITransformation intensityTransform = new IntensityTransform();

    IImageState intensityImage = intensityTransform.apply(sourceImage);

    model.add(destId, intensityImage);
  }
}

