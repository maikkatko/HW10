import org.junit.Test;

import java.io.StringReader;

import controller.ControllerImpl;
import controller.IController;
import model.IModel;
import model.ImageDatabase;
import view.IView;
import view.ViewImpl;

/**
 * This class tests the functionality of the view given various valid and invalid inputs.
 */
public class ViewTest {

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    IModel model = new ImageDatabase();
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage");
    Appendable log = new FailingAppendable();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputsToViewFirstConstructor() {
    IView view = new ViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLogToViewSecondConstructor() {
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelToViewSecondConstructor() {
    Appendable log = new StringBuilder();
    IView view = new ViewImpl(null, log);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputsToViewSecondConstructor() {
    IView view = new ViewImpl(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputToRenderMessage() {
    IView view = new ViewImpl(null, null);
  }
}
