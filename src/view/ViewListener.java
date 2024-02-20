package view;

import java.io.File;

/**
 * This interface represents the handlers used by ControllerGUI for running functionality.
 */
public interface ViewListener {
  /**
   * Runs the correct load functionality given input from an emitter.
   *
   * @param pathName a String representing a file path
   */
  void handleLoadEvent(String pathName);

  /**
   * Runs the correct save functionality given input from an emitter.
   *
   * @param file a File object representing a file to save
   */
  void handleSaveEvent(File file);

  /**
   * Runs the red-component greyscale transform when triggered by an emitter.
   */
  void handleRedEvent();

  /**
   * Runs the green-component greyscale transform when triggered by an emitter.
   */
  void handleGreenEvent();

  /**
   * Runs the blue-component greyscale transform when triggered by an emitter.
   */
  void handleBlueEvent();

  /**
   * Runs the blur transform when triggered by an emitter.
   */
  void handleBlurEvent();

  /**
   * Runs the sharpen transform when triggered by an emitter.
   */
  void handleSharpenEvent();

  /**
   * Runs the sepia transform when triggered by an emitter.
   */
  void handleSepiaEvent();

  /**
   * Runs the brightness transform when triggered by an emitter.
   */
  void handleBrightnessEvent(int value);

  /**
   * Runs the value-component greyscale transform when triggered by an emitter.
   */
  void handleValueEvent();

  /**
   * Runs the intensity-component greyscale transform when triggered by an emitter.
   */
  void handleIntensityEvent();

  /**
   * Runs the luma-component greyscale transform when triggered by an emitter.
   */
  void handleLumaEvent();
}
