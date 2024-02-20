package controller.io;

/**
 * This interface represents an image saver for taking images from the Image Database and saving
 * them to a given path.
 */
public interface IImageSaver {

  /**
   * This methods the necessary operations specific to a given file format.
   */
  void run();
}
