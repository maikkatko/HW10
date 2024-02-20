import java.awt.Color;
import java.awt.image.BufferedImage;

import view.ViewGUI;
import view.ViewListener;

/**
 * This class implements the setIMage method for a mock view to test the controller sending
 * images to the view.
 */
public class MockView extends ViewGUI {
  private StringBuilder log;

  /**
   * This constructor initializes the log.
   *
   * @param log a StringBuilder object representing the log
   */
  MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addViewListeners(ViewListener listener) {
    // Not needed for testing
  }

  @Override
  public void setViewText(String text) {
    // Not needed for testing
  }

  @Override
  public void errorHandler(String error, String errorTitle) {
    // Not needed for testing
  }

  @Override
  public void setImage(BufferedImage image) {
    for ( int x = 0; x < image.getWidth(); x++) {
      for ( int y = 0; y < image.getHeight(); y++) {
        Color color = new Color(image.getRGB(x, y));

        this.log.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
      }
    }
  }
}
