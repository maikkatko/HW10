package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents all the functionality for building the graphical user interface as well
 * as establishing a list of subscribers to a set of emitters that can allow for input in the GUI
 * to be handled by the controller.
 */
public class ViewGUI extends JFrame implements ActionListener, IViewGUI {
  private final HistogramPanel histogramPanel;
  private final JLabel showText;
  private final ArrayList<ViewListener> listenersToNotify;
  private final Canvas canvas;

  /**
   * This constructor initializes all the fields for the GUI as well as the JFrame itself.
   */
  public ViewGUI() {
    // Initialize buttons
    JButton saveDataButton;
    JButton loadDataButton;
    JButton brightnessButton;
    JButton redFilterButton;
    JButton greenFilterButton;
    JButton blueFilterButton;
    JButton valueFilterButton;
    JButton intensityFilterButton;
    JButton lumaFilterButton;
    JButton blurButton;
    JButton sharpenButton;
    JButton sepiaButton;
    JToolBar editsToolBar;
    JToolBar fileToolBar;

    // Initialize empty canvas and histogram, as well as initializing subscriber list
    this.canvas = new Canvas(null);
    this.histogramPanel = new HistogramPanel();
    this.listenersToNotify = new ArrayList<>();

    // Initialize the JFrame itself
    setSize(1400, 1000);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);

    // Create a toolbar for edit features and for File IO features
    editsToolBar = new JToolBar("Edits");
    editsToolBar.setOrientation(JToolBar.VERTICAL);
    fileToolBar = new JToolBar("File IO");
    fileToolBar.setOrientation(JToolBar.VERTICAL);

    // Create all the buttons necessary for the functioning of the program
    saveDataButton = new JButton("Save Image");
    loadDataButton = new JButton("Load Image");
    redFilterButton = new JButton("Red Greyscale");
    greenFilterButton = new JButton("Green Greyscale");
    blueFilterButton = new JButton("Blue Greyscale");
    valueFilterButton = new JButton("Value Greyscale");
    intensityFilterButton = new JButton("Intensity Greyscale");
    lumaFilterButton = new JButton("Luma Greyscale");
    blurButton = new JButton("Blur Image");
    sharpenButton = new JButton("Sharpen Image");
    sepiaButton = new JButton("Sepia Image");
    brightnessButton = new JButton("Change Brightness");

    // Generate a toolbar for loading and saving images
    fileToolBar.add(loadDataButton);
    fileToolBar.add(saveDataButton);

    // Generate a toolbar for all buttons tied to image transforms
    editsToolBar.add(redFilterButton);
    editsToolBar.add(greenFilterButton);
    editsToolBar.add(blueFilterButton);
    editsToolBar.add(valueFilterButton);
    editsToolBar.add(intensityFilterButton);
    editsToolBar.add(lumaFilterButton);
    editsToolBar.add(blurButton);
    editsToolBar.add(sharpenButton);
    editsToolBar.add(sepiaButton);
    editsToolBar.add(brightnessButton);
    showText = new JLabel("Load an Image to apply transforms");

    // Create a scroll pane so the image has scroll bars using the canvas
    JScrollPane scrollPane = new JScrollPane(canvas);

    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Create a splitPane to hold the scrollPane and the histogram
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, histogramPanel);
    splitPane.setResizeWeight(0.75);

    // Set up the JFrame with each of the elements
    add(fileToolBar, BorderLayout.EAST);
    add(editsToolBar, BorderLayout.WEST);
    add(splitPane, BorderLayout.CENTER);
    add(showText, BorderLayout.SOUTH);

    // set an action command and add an action listener to each button
    saveDataButton.setActionCommand("save");
    loadDataButton.setActionCommand("load");
    redFilterButton.setActionCommand("red");
    greenFilterButton.setActionCommand("green");
    blueFilterButton.setActionCommand("blue");
    valueFilterButton.setActionCommand("value");
    intensityFilterButton.setActionCommand("intensity");
    lumaFilterButton.setActionCommand("luma");
    blurButton.setActionCommand("blur");
    sharpenButton.setActionCommand("sharpen");
    sepiaButton.setActionCommand("sepia");
    brightnessButton.setActionCommand("brightness");
    saveDataButton.addActionListener(this);
    loadDataButton.addActionListener(this);
    redFilterButton.addActionListener(this);
    greenFilterButton.addActionListener(this);
    blueFilterButton.addActionListener(this);
    valueFilterButton.addActionListener(this);
    intensityFilterButton.addActionListener(this);
    lumaFilterButton.addActionListener(this);
    blurButton.addActionListener(this);
    sharpenButton.addActionListener(this);
    sepiaButton.addActionListener(this);
    brightnessButton.addActionListener(this);
  }

  @Override
  public void addViewListeners(ViewListener listener) {
    this.listenersToNotify.add(listener);
  }

  @Override
  public void setViewText(String text) {
    this.showText.setText(text);
  }

  /**
   * Calculates the Histogram values for each color channel.
   *
   * @param image a BufferedImage object representing the image
   * @return an int[][] array representing the levels of each color channel
   */
  private int[][] calculateHistograms(BufferedImage image) {
    int[][] histograms = new int[3][256]; // 2D array to store histograms for each color channel
    int width = image.getWidth();
    int height = image.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);

        // Extract the color values for Red, Green, and Blue channels
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Increment the corresponding histogram bins for each color channel
        histograms[0][red]++;
        histograms[1][green]++;
        histograms[2][blue]++;
      }
    }

    return histograms;
  }

  @Override
  public void errorHandler(String error, String errorTitle) {
    JOptionPane.showMessageDialog(null, error, errorTitle, JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setImage(BufferedImage image) {
    int[][] histogramData = calculateHistograms(image);
    this.histogramPanel.setHistogramData(histogramData);
    this.histogramPanel.repaint();

    this.canvas.setImage(image);
  }

  /**
   * Opens a file load dialog and gets the filename.
   *
   * @return a String representing the filename
   */
  public String loadDialog() {
    Frame frame = new Frame("Select an Image");

    // Create a new FileDialog instance
    FileDialog fileDialog = new FileDialog(frame, "Open File", FileDialog.LOAD);

    // Display the file dialog
    fileDialog.setVisible(true);

    // Get the selected file path and name
    String directory = fileDialog.getDirectory();
    String file = fileDialog.getFile();

    if (file != null) {
      // Check the file format
      if (file.endsWith(".jpeg")
          || file.endsWith(".jpg")
          || file.endsWith(".png")
          || file.endsWith(".bmp")
          || (file.endsWith(".ppm"))) {
        // Handle the selected file (correct format), e.g., read its contents or use it as needed
        String filePath = directory + file;
        this.showText.setText("Selected file: " + filePath);
        frame.dispose();
        return directory + file;
      } else {
        // Show an error message if the file format is incorrect
        String errorMessage = "Invalid file format. Please select a JPEG/PNG/PPM/BMP file.";
        JOptionPane.showMessageDialog(null,
                                      errorMessage,
                                  "File Format Error",
                                      JOptionPane.ERROR_MESSAGE);
        return null;
      }
    } else {
      // Handle the case when the user canceled the file dialog or closed the dialog
      this.showText.setText("File selection canceled.");
    }
    // Dispose the Frame after using the FileDialog
    return null;
  }

  /**
   * This emitter opens a load file dialog and calls the load handler in any subscriber (listener).
   */
  private void emitLoadEvent() {
    for (ViewListener listener: listenersToNotify) {
      String filename = loadDialog();

      if (filename != null) {
        listener.handleLoadEvent(filename);
      }
    }
  }

  /**
   * This emitter opens a save file dialog and calls the save handler in any subscriber (listener).
   */
  private void emitSaveEvent() {
    for (ViewListener listener : listenersToNotify) {
      //Create a file chooser and filter for acceptable images
      JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Images (*.jpg, *.jpeg, *.png,*.bmp, *.ppm)",
              "jpg", "jpeg", "png", "bmp", "ppm");
      fileChooser.setFileFilter(filter);

      // Show the save dialog
      int userSelection = fileChooser.showSaveDialog(this);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        listener.handleSaveEvent(fileToSave);
      }
    }
  }

  /**
   * This emitter calls the red handler in any subscriber (listener) to apply a red-component
   * greyscale transform.
   */
  private void emitRedEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleRedEvent();
    }
  }

  /**
   * This emitter calls the green handler in any subscriber (listener) to apply a green-component
   * greyscale transform.
   */
  private void emitGreenEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleGreenEvent();
    }
  }

  /**
   * This emitter calls the blue handler in any subscriber (listener) to apply a blue-component
   * greyscale transform.
   */
  private void emitBlueEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleBlueEvent();
    }
  }

  /**
   * This emitter calls the brightness handler in any subscriber (listener) to apply a brightness
   * transform.
   */
  private void emitBrightnessEvent() {
    for (ViewListener listener : listenersToNotify) {
      // Pop-up for user to enter value to change brightness by
      String input = JOptionPane.showInputDialog(this, "Enter an integer value:");

      if (input != null && !input.isEmpty()) {
        try {
          int value = Integer.parseInt(input);
          listener.handleBrightnessEvent(value);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this,
                  "Invalid input. Please enter a valid integer.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  /**
   * This emitter calls the value handler in any subscriber (listener) to apply a value-component
   * greyscale transform.
   */
  private void emitValueEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleValueEvent();
    }
  }

  /**
   * This emitter calls the intensity handler in any subscriber (listener) to apply a
   * intensity-component greyscale transform.
   */
  private void emitIntensityEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleIntensityEvent();
    }
  }

  /**
   * This emitter calls the luma handler in any subscriber (listener) to apply a luma-component
   * greyscale transform.
   */
  private void emitLumaEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleLumaEvent();
    }
  }

  /**
   * This emitter calls the blur handler in any subscriber (listener) to apply a blur transform.
   */
  private void emitBlurEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleBlurEvent();
    }
  }

  /**
   * This emitter calls the sharpen handler in any subscriber (listener) to apply a sharpen
   * transform.
   */
  private void emitSharpenEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleSharpenEvent();
    }
  }

  /**
   * This emitter calls the sepia handler in any subscriber (listener) to apply a sepia transform.
   */
  private void emitSepiaEvent() {
    for (ViewListener listener: listenersToNotify) {
      listener.handleSepiaEvent();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //Which button did we click?
    switch ( e.getActionCommand() ) {
      case "load":
        emitLoadEvent();
        break;
      case "save":
        emitSaveEvent();
        break;
      case "red":
        emitRedEvent();
        break;
      case "green":
        emitGreenEvent();
        break;
      case "blue":
        emitBlueEvent();
        break;
      case "value":
        emitValueEvent();
        break;
      case "intensity":
        emitIntensityEvent();
        break;
      case "luma":
        emitLumaEvent();
        break;
      case "blur":
        emitBlurEvent();
        break;
      case "sharpen":
        emitSharpenEvent();
        break;
      case "sepia":
        emitSepiaEvent();
        break;
      case "brightness":
        emitBrightnessEvent();
        break;
      default:
        throw new IllegalStateException("Unknown Action Command");
    }
  }
}
