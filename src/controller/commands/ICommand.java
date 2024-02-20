package controller.commands;

import java.util.Scanner;

import model.IModel;
import view.IView;

/**
 * This interface represents the commands necessary to run all needed functionality of the
 * program including but not limited to the following.
 * Load - Loading an image into the ImageDatabase from a path
 * Save - Save an image from the ImageDatabase to a given path
 * All Else - Call a specific transform strategy and save a copy to the ImagDatabase
 */
public interface ICommand {
  /**
   * Runs a specific command given a scanner, model and view from the controller.
   *
   * @param scanner A Scanner object representing the input of the command
   * @param model An IModel object representing the model
   * @param view An IView object representing the view
   */
  void run(Scanner scanner, IModel model, IView view);
}
