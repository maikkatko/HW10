package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.io.IImageLoader;
import controller.io.IImageSaver;
import controller.io.ImageLoader;
import controller.io.PPMImageSaver;
import model.IImageState;
import model.IModel;
import model.strategies.BrightnessTransform;
import model.strategies.FilterTransform;
import model.strategies.ITransformation;
import model.strategies.IntensityTransform;
import model.strategies.KernelTransform;
import model.strategies.ValueTransform;
import view.ViewGUI;
import view.ViewListener;

/**
 * This class represents the controller for the ViewGUI to handle input from a GUI.
 */
public class ControllerGUI implements IController, ViewListener {
  private IModel model;
  private ViewGUI view;
  private String currentImageId;
  private int imageNumber;

  /**
   * This constructor initializes the model, view, and initial String and int for generating
   * unique image id's,and lastly adds this controller as a subscriber to the views emitters.
   *
   * @param model an IModel object representing the image database
   * @param view a ViewGUI object representing the GUI view
   */
  public ControllerGUI(IModel model, ViewGUI view) {
    this.model = model;
    this.view = view;
    this.currentImageId = "";
    this.imageNumber = 0;
    this.view.addViewListeners(this);
  }

  @Override
  public void handleLoadEvent(String filename) {
    int lastSlashIndex;

    // Generate a BufferedImage to give to the view to display
    try {
      BufferedImage image = ImageIO.read(new File(filename));
      this.view.setImage(image);
    } catch (IOException e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }

    // Check for either forward slash or backslash to get imageId
    if (filename.contains("/")) {
      lastSlashIndex = filename.lastIndexOf("/");
      int lastDotIndex = filename.lastIndexOf(".");

      // set imageId to the text between the last slash and the last dot
      String imageId = filename.substring(lastSlashIndex + 1, lastDotIndex);

      this.currentImageId = imageId;
      IImageLoader loader = new ImageLoader();
      IImageState newImage = loader.run(filename);
      model.add(imageId, newImage);
    } else if (filename.contains("\\")) {
      lastSlashIndex = filename.lastIndexOf("\\");
      int lastDotIndex = filename.lastIndexOf(".");

      // set imageId to the text between the last slash and the last dot
      String imageId = filename.substring(lastSlashIndex + 1, lastDotIndex);

      this.currentImageId = imageId;
      IImageLoader loader = new ImageLoader();
      IImageState newImage = loader.run(filename);
      model.add(imageId, newImage);
    }
  }

  @Override
  public void handleSaveEvent(File file) {
    String filename = file.getName();
    String fileType = filename.split("\\.")[1];

    // Check for ppm or other image formats and call the correct image saver
    if (fileType.equalsIgnoreCase("ppm")) {
      IImageSaver saver = new PPMImageSaver(file.getPath(), model.get(this.currentImageId));
      saver.run();
    } else if (fileType.equalsIgnoreCase("jpg")
            || fileType.equalsIgnoreCase("jpeg")
            || fileType.equalsIgnoreCase("png")
            || fileType.equalsIgnoreCase("bmp")) {

      // Use ImageHandler to take the IImageState image and convert to BufferedImage
      IImageHandler handler = new ImageHandler();
      BufferedImage imageToSave = handler.run(model.get(this.currentImageId));

      // Write the BufferedImage to the file
      try {
        ImageIO.write(imageToSave, fileType, file);
      } catch (IOException e) {
        this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
      }
    }
  }

  /**
   * Generates unique image id's for adding images to the model.
   *
   * @return a String representing a unique image id
   */
  private String newImageID() {
    String newImageId = this.currentImageId + this.imageNumber;
    this.currentImageId = newImageId;
    this.imageNumber++;
    return newImageId;
  }

  @Override
  public void handleRedEvent() {
    // initialize the correct matrix for red-component greyscale
    double[][] matrix = new double[][] {
            {1d, 0d, 0d},
            {1d, 0d, 0d},
            {1d, 0d, 0d}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation redTransform = new FilterTransform(matrix);
      IImageState newImage = redTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Red Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleGreenEvent() {
    // initialize the correct matrix for green-component greyscale
    double[][] matrix = new double[][] {
            {0d, 1d, 0d},
            {0d, 1d, 0d},
            {0d, 1d, 0d}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation greenTransform = new FilterTransform(matrix);
      IImageState newImage = greenTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Green Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleBlueEvent() {
    // initialize the correct matrix for blue-component greyscale
    double[][] matrix = new double[][] {
            {0d, 0d, 1d},
            {0d, 0d, 1d},
            {0d, 0d, 1d}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation blueTransform = new FilterTransform(matrix);
      IImageState newImage = blueTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Blue Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleValueEvent() {
    try {
      // Call the correct transform and apply to current image
      ITransformation valueTransform = new ValueTransform();
      IImageState newImage = valueTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Value Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleIntensityEvent() {
    try {
      // Call the correct transform and apply to current image
      ITransformation intensityTransform = new IntensityTransform();
      IImageState newImage = intensityTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Intensity Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleLumaEvent() {
    // initialize the correct matrix for luma-component greyscale
    double[][] matrix = new double[][] {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation lumaTransform = new FilterTransform(matrix);
      IImageState newImage = lumaTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Luma Component Greyscale Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleBlurEvent() {
    // initialize the correct kernel for the blur
    double[][] kernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation blurTransform = new KernelTransform(kernel);
      IImageState newImage = blurTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Blur Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleSharpenEvent() {
    // initialize the correct kernel for sharpening
    double[][] kernel = new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation sharpenTransform = new KernelTransform(kernel);
      IImageState newImage = sharpenTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Sharpen Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleSepiaEvent() {
    // initialize the correct matrix for a sepia transform
    double[][] matrix = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };

    try {
      // Call the correct transform and apply to current image
      ITransformation blueTransform = new FilterTransform(matrix);
      IImageState newImage = blueTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Sepia Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void handleBrightnessEvent(int value) {
    try {
      // Call the correct transform and apply to current image
      ITransformation brightnessTransform = new BrightnessTransform(value);
      IImageState newImage = brightnessTransform.apply(model.get(this.currentImageId));

      // Convert the current image to a BufferedImage to give to the view to display
      IImageHandler handler = new ImageHandler();
      BufferedImage buffImage = handler.run(newImage);

      // Add the transformed image to the image database and give the view the image to display as
      // well as a message
      this.model.add(newImageID(), newImage);
      this.view.setImage(buffImage);
      this.view.setViewText("Applied Brightness Transform");
    } catch (Exception e) {
      this.view.errorHandler(e.getMessage(), e.getClass().getSimpleName());
    }
  }

  @Override
  public void run() {
    // Show the GUI
    this.view.setVisible(true);
  }
}
