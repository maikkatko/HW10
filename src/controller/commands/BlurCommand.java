package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.ITransformation;
import model.strategies.KernelTransform;
import view.IView;

/**
 * This class implements the ICommand interface for a blur transformation. This involves parsing
 * a scanner for the data needed and then calling the blurTransform strategy.
 */
public class BlurCommand implements ICommand {
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

    double[][] kernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    ITransformation kernelTransform = new KernelTransform(kernel);

    IImageState blurImage = kernelTransform.apply(sourceImage);

    model.add(destId, blurImage);
  }
}
