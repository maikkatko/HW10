package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.FilterTransform;
import model.strategies.ITransformation;
import view.IView;

/**
 * This class implements the ICommand interface for a sepia transformation. This involves
 * parsing a scanner for the data needed and then calling the sepiaTransform strategy.
 */
public class SepiaCommand implements ICommand {
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

    double[][] matrix = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };

    ITransformation filterTransform = new FilterTransform(matrix);

    IImageState sepiaImage = filterTransform.apply(sourceImage);

    model.add(destId, sepiaImage);
  }
}
