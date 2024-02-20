package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageState;
import model.IModel;
import model.strategies.FilterTransform;
import model.strategies.ITransformation;
import view.IView;

/**
 * This class implements the ICommand interface for a luma component greyscale transformation.
 * This involves parsing a scanner for the data needed and then calling the lumaTransform strategy.
 */
public class LumaCommand implements ICommand {
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

    double[][] matrix = new double[][] {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };

    ITransformation filterTransform = new FilterTransform(matrix);

    IImageState lumaImage = filterTransform.apply(sourceImage);

    model.add(destId, lumaImage);
  }
}

