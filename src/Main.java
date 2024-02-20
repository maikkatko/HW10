import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ControllerGUI;
import controller.ControllerImpl;
import controller.IController;
import model.IModel;
import model.ImageDatabase;
import view.IView;
import view.ViewGUI;
import view.ViewImpl;

/**
 * This class runs the program given user input from the command line.
 */
public final class Main {
  /**
   * Calls the correct model, view, and controller based on user input, or runs a txt script file
   * given by the user to enter commands.
   *
   * @param args a String[] object representing the inputs
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      IModel model = new ImageDatabase();
      ViewGUI viewGUI = new ViewGUI();
      IController controller = new ControllerGUI(model, viewGUI);
      controller.run();
      return;
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-file")) {
      StringBuilder txtCommands = new StringBuilder();

      File file = new File(args[1]);

      try {
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
          txtCommands.append(sc.next() + " ");
        }

        Readable in = new StringReader(txtCommands.toString());
        Appendable log = new StringBuilder();
        IModel model = new ImageDatabase();
        IView view = new ViewImpl(model, log);
        IController c = new ControllerImpl(model, view, in);
        c.run();

      } catch (FileNotFoundException e) {
        throw new IllegalStateException("File could not be found");
      }

      return;
    }

    System.out.println("Please enter a command: ");

    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, new InputStreamReader(System.in));
    c.run();
  }
}