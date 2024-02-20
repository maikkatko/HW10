package view;



import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;


/**
 * This class generates a histogram to add to the GUI.
 */
public class HistogramPanel extends JPanel {
  private int[][] histogramData; // Array to store the histogram data

  /**
   * Updates the histogram with new data.
   *
   * @param histogramData an int[][] array representing the histogram
   */
  public void setHistogramData(int[][] histogramData) {
    this.histogramData = histogramData;
    repaint(); // Trigger the repaint to update the histogram
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (histogramData != null) {
      drawHistogram(g);
    }
  }

  /**
   * Draws the histogram with individual colors for each channel.
   *
   * @param g a Graphics object representing the graphic itself
   */
  private void drawHistogram(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    int barWidth = width / histogramData[0].length;
    int channelHeight = height / 3; // Divide the height to display three channels

    for (int channel = 0; channel < 3; channel++) {
      int[] channelData = histogramData[channel];

      // Find the maximum value in the histogram data for this channel
      int maxCount = 0;
      for (int count : channelData) {
        maxCount = Math.max(maxCount, count);
      }

      // Draw histogram bars for this channel
      for (int i = 0; i < channelData.length; i++) {
        int barHeight = (int) ((double) channelData[i] / maxCount * (channelHeight - 2));
        int x = i * barWidth;
        int y = channel * channelHeight + channelHeight - barHeight - 1;

        switch (channel) {
          case 0:
            g.setColor(new Color(255, 0, 0)); // Red channel
            break;
          case 1:
            g.setColor(new Color(0, 255, 0)); // Green channel
            break;
          case 2:
            g.setColor(new Color(0, 0, 255)); // Blue channel
            break;
          default:
            continue;
        }

        g.fillRect(x, y, barWidth, barHeight);
      }
    }
  }
}
