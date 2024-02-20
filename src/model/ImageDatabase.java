package model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the IModel interface and represents a database of images uytiliznig a
 * hash map for storing IImageState objects with a given image id as a key.
 */
public class ImageDatabase implements IModel {
  private Map<String, IImageState> imageMap;

  /**
   * This constructor initializes an empty hash map which can be filled with images.
   */
  public ImageDatabase() {
    this.imageMap = new HashMap<>();
  }

  @Override
  public void add(String imageId, IImageState image) {
    if (image == null) {
      throw new IllegalStateException("image is null");
    }

    imageMap.put(imageId, image);
  }

  @Override
  public IImageState get(String idImage) {
    IImageState image = imageMap.get(idImage);

    if (image == null) {
      throw new IllegalStateException("image is null");
    }

    return imageMap.get(idImage);
  }
}
