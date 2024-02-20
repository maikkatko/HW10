package model;

/**
 * This interface represents an image database model, complete with an add and a get method for
 * adding and getting images from the database.
 */
public interface IModel {

  /**
   * Adds an image to the Image Database.
   *
   * @param imageId a String representing the image Id
   * @param image an IImageState object representing the image to add
   */
  void add(String imageId, IImageState image);

  /**
   * Gets an image with a given id from the image database.
   *
   * @param idImage a String representing the image id
   * @return an IImageState object representing the image
   */
  IImageState get(String idImage);
}
