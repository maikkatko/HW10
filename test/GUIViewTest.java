import org.junit.Test;

import controller.ControllerGUI;
import model.IImageState;
import model.IModel;
import model.ImageDatabase;
import view.ViewListener;

import static junit.framework.TestCase.assertEquals;

/**
 * This class tests the mock view to verify that the controller is properly communicating with
 * the view.
 */
public class GUIViewTest {
  @Test
  public void testMockView() {
    StringBuilder log = new StringBuilder();
    IModel model = new ImageDatabase();
    MockView mockView = new MockView(log);
    ViewListener controller = new ControllerGUI(model, mockView);
    controller.handleLoadEvent("res/panda.png");

    IImageState image = model.get("panda");

    StringBuilder expected = new StringBuilder();

    // Build a string builder to test against
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        expected.append(image.getRedChannel(y, x) + " " + image.getGreenChannel(y, x) + " "
                          + image.getBlueChannel(y, x) + " ");
      }
    }

    assertEquals(expected.toString(), log.toString());
  }
}
