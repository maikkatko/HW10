package view;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/**
 * This class represents the canvas that an image will be displayed on.
 */
public class Canvas extends JPanel {
  private BufferedImage image;

  /**
   * This constructor initializes an image.
   *
   * @param image a BufferedImage object representing an image
   */
  public Canvas(BufferedImage image) {
    this.image = image;
  }

  /**
   * Sets a new image to the canvas.
   *
   * @param image a BufferedImage object representing the iamge
   */
  public void setImage(BufferedImage image) {
    this.image = image;
    repaint(); // Trigger the repaint to display the new image
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.gray);

    g.drawImage(this.image, 0, 0, this);
  }
}
