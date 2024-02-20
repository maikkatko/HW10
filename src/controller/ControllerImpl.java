package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import controller.commands.BlueCommand;
import controller.commands.BlurCommand;
import controller.commands.BrightnessCommand;
import controller.commands.GreenCommand;
import controller.commands.ICommand;
import controller.commands.IntensityCommand;
import controller.commands.LoadCommand;
import controller.commands.LumaCommand;
import controller.commands.RedCommand;
import controller.commands.SaveCommand;
import controller.commands.SepiaCommand;
import controller.commands.SharpenCommand;
import controller.commands.ValueCommand;
import model.IModel;
import view.IView;

/**
 * This class implements the IController interface. It takes a model, view and a readable in
 * order to parse commands to send to the model and record results.
 */
public class ControllerImpl implements IController {
  private IModel model;
  private IView view;
  private final Map<String, ICommand> commands;
  private Readable in;

  /**
   * This constructor takes a model, view and Readable in order to initialize the controller and
   * generate a hash map of commands.
   *
   * @param model an IModel object representing the model
   * @param view an IView object representing the model
   * @param in a Readable object representing the input from the user
   * @throws IllegalArgumentException in case worng inputs are given
   */
  public ControllerImpl(IModel model, IView view, Readable in) throws IllegalArgumentException {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.in = Objects.requireNonNull(in);
    this.commands = new HashMap<>();

    this.commands.put("load", new LoadCommand());
    this.commands.put("save", new SaveCommand());
    this.commands.put("red-component", new RedCommand());
    this.commands.put("green-component", new GreenCommand());
    this.commands.put("blue-component", new BlueCommand());
    this.commands.put("value-component", new ValueCommand());
    this.commands.put("intensity-component", new IntensityCommand());
    this.commands.put("luma-component", new LumaCommand());
    this.commands.put("brighten", new BrightnessCommand());
    this.commands.put("darken", new BrightnessCommand());
    this.commands.put("blur", new BlurCommand());
    this.commands.put("sharpen", new SharpenCommand());
    this.commands.put("sepia", new SepiaCommand());
  }



  @Override
  public void run() {
    Scanner scanner = new Scanner(this.in);

    while (scanner.hasNext()) {
      String stringCommand = scanner.next().toLowerCase();
      if (stringCommand.equalsIgnoreCase("quit")) {
        return;
      }

      ICommand command = this.commands.getOrDefault(stringCommand, null);

      if ( command == null ) {
        try {
          view.renderMessage("Invalid Command");
          continue;
        } catch (IOException | NullPointerException e) {
          throw new IllegalStateException("Invalid state");
        }
      }

      try {
        command.run(scanner, this.model, this.view);
      } catch (IllegalStateException e) {
        try {
          this.view.renderMessage(e.getMessage());
        } catch (IOException f) {
          throw new IllegalStateException("Invalid state");
        }
      }
    }

    throw new IllegalStateException("User never quit");
  }
}
