import org.junit.Test;

import java.io.StringReader;

import controller.ControllerImpl;
import controller.IController;
import model.IModel;
import model.ImageDatabase;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class tests the functionality of the controller given various valid and invalid inputs.
 */
public class ControllerTest {
  @Test
  public void testMockModel() {
    Readable in = new StringReader("load images/testImage.ppm testImage quit");
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, 124356);
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("testImage 124356", log.toString());
  }

  @Test
  public void testInvalidFirstArgThenValid() {
    Readable in = new StringReader("visualize images/test_img_4x4.ppm testImage "
            + "load images/test_img_4x4.jpg testImg quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test(expected = IllegalStateException.class)
  public void testNoQuit() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();
  }

  @Test
  public void testLoadCommandPPM() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage save "
            + "images/test_img_4x4_saved.ppm testImage quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 120, 96, 147\n"
                    + "Colors at Pixel (0, 1): 121, 185, 76\n"
                    + "Colors at Pixel (0, 2): 21, 203, 117\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 96, 97, 98\n"
                    + "Colors at Pixel (1, 1): 15, 127, 240\n"
                    + "Colors at Pixel (1, 2): 100, 118, 130\n"
                    + "Colors at Pixel (1, 3): 156, 134, 100\n"
                    + "Colors at Pixel (2, 0): 87, 56, 120\n"
                    + "Colors at Pixel (2, 1): 125, 145, 185\n"
                    + "Colors at Pixel (2, 2): 210, 215, 240\n"
                    + "Colors at Pixel (2, 3): 76, 152, 215\n"
                    + "Colors at Pixel (3, 0): 145, 155, 137\n"
                    + "Colors at Pixel (3, 1): 189, 195, 175\n"
                    + "Colors at Pixel (3, 2): 21, 84, 36\n"
                    + "Colors at Pixel (3, 3): 165, 121, 213\n",
            view.toString("testImage"));
  }

  @Test
  public void testLoadSaveCommandJPEG() {
    Readable in = new StringReader("load images/test_img_4x4.jpeg testImg save "
            + "images/test_img_4x4_JPEG.jpeg testImg load images/test_img_4x4_JPEG.jpeg "
            + "testImgJPG quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    try {
      model.get("testImgJPG");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testLoadSaveCommandPNG() {
    Readable in = new StringReader("load images/test_img_4x4.png testImg save "
            + "images/test_img_4x4_PNG.png testImg load images/test_img_4x4_PNG.png "
            + "testImgPNG quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    try {
      model.get("testImgPNG");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testLoadSaveCommandBMP() {
    Readable in = new StringReader("load images/test_img_4x4.bmp testImg save "
            + "images/test_img_4x4_BMP.bmp testImg load images/test_img_4x4_BMP.bmp "
            + "testImgBMP quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    try {
      model.get("testImgBMP");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testLoadJPGSaveBMP() {
    Readable in = new StringReader("load images/test_img_4x4.jpg testImg save "
            + "images/test_img_4x4_BMP.bmp testImg load images/test_img_4x4_BMP.bmp "
            + "testImgBMP quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    try {
      model.get("testImgBMP");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testInvalidLoadCommandThenValid() {
    Readable in = new StringReader("load images/test_img_ testImage load "
            + "images/test_img_4x4.jpeg testImg quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    try {
      model.get("testImg");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testBrightnessCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "brighten 10 testImage testImageBright quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 130, 106, 157\n"
                    + "Colors at Pixel (0, 1): 131, 195, 86\n"
                    + "Colors at Pixel (0, 2): 31, 213, 127\n"
                    + "Colors at Pixel (0, 3): 127, 127, 127\n"
                    + "Colors at Pixel (1, 0): 106, 107, 108\n"
                    + "Colors at Pixel (1, 1): 25, 137, 250\n"
                    + "Colors at Pixel (1, 2): 110, 128, 140\n"
                    + "Colors at Pixel (1, 3): 166, 144, 110\n"
                    + "Colors at Pixel (2, 0): 97, 66, 130\n"
                    + "Colors at Pixel (2, 1): 135, 155, 195\n"
                    + "Colors at Pixel (2, 2): 220, 225, 250\n"
                    + "Colors at Pixel (2, 3): 86, 162, 225\n"
                    + "Colors at Pixel (3, 0): 155, 165, 147\n"
                    + "Colors at Pixel (3, 1): 199, 205, 185\n"
                    + "Colors at Pixel (3, 2): 31, 94, 46\n"
                    + "Colors at Pixel (3, 3): 175, 131, 223\n",
            view.toString("testImageBright"));
  }

  @Test
  public void testInvalidBrightness() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "brighten ten testImage testImageBright quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Second arg must be int\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidBrightnessCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "brighten 10 10 testImageBright quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidBrightnessCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "brighten 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testRedCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "red-component testImage testImageRed quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 120, 120, 120\n"
                    + "Colors at Pixel (0, 1): 121, 121, 121\n"
                    + "Colors at Pixel (0, 2): 21, 21, 21\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 96, 96, 96\n"
                    + "Colors at Pixel (1, 1): 15, 15, 15\n"
                    + "Colors at Pixel (1, 2): 100, 100, 100\n"
                    + "Colors at Pixel (1, 3): 156, 156, 156\n"
                    + "Colors at Pixel (2, 0): 87, 87, 87\n"
                    + "Colors at Pixel (2, 1): 125, 125, 125\n"
                    + "Colors at Pixel (2, 2): 210, 210, 210\n"
                    + "Colors at Pixel (2, 3): 76, 76, 76\n"
                    + "Colors at Pixel (3, 0): 145, 145, 145\n"
                    + "Colors at Pixel (3, 1): 189, 189, 189\n"
                    + "Colors at Pixel (3, 2): 21, 21, 21\n"
                    + "Colors at Pixel (3, 3): 165, 165, 165\n",
            view.toString("testImageRed"));
  }

  @Test
  public void testInvalidRedCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "red-component 10 10 testImageRed quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidRedCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "red-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testGreenCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "green-component testImage testImageGreen quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 96, 96, 96\n"
                    + "Colors at Pixel (0, 1): 185, 185, 185\n"
                    + "Colors at Pixel (0, 2): 203, 203, 203\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 97, 97, 97\n"
                    + "Colors at Pixel (1, 1): 127, 127, 127\n"
                    + "Colors at Pixel (1, 2): 118, 118, 118\n"
                    + "Colors at Pixel (1, 3): 134, 134, 134\n"
                    + "Colors at Pixel (2, 0): 56, 56, 56\n"
                    + "Colors at Pixel (2, 1): 145, 145, 145\n"
                    + "Colors at Pixel (2, 2): 215, 215, 215\n"
                    + "Colors at Pixel (2, 3): 152, 152, 152\n"
                    + "Colors at Pixel (3, 0): 155, 155, 155\n"
                    + "Colors at Pixel (3, 1): 195, 195, 195\n"
                    + "Colors at Pixel (3, 2): 84, 84, 84\n"
                    + "Colors at Pixel (3, 3): 121, 121, 121\n",
            view.toString("testImageGreen"));
  }


  @Test
  public void testInvalidGreenCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "green-component 10 10 testImageGreen quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidGreenCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "green-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testBlueCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "blue-component testImage testImageBlue quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 147, 147, 147\n"
                    + "Colors at Pixel (0, 1): 76, 76, 76\n"
                    + "Colors at Pixel (0, 2): 117, 117, 117\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 98, 98, 98\n"
                    + "Colors at Pixel (1, 1): 240, 240, 240\n"
                    + "Colors at Pixel (1, 2): 130, 130, 130\n"
                    + "Colors at Pixel (1, 3): 100, 100, 100\n"
                    + "Colors at Pixel (2, 0): 120, 120, 120\n"
                    + "Colors at Pixel (2, 1): 185, 185, 185\n"
                    + "Colors at Pixel (2, 2): 240, 240, 240\n"
                    + "Colors at Pixel (2, 3): 215, 215, 215\n"
                    + "Colors at Pixel (3, 0): 137, 137, 137\n"
                    + "Colors at Pixel (3, 1): 175, 175, 175\n"
                    + "Colors at Pixel (3, 2): 36, 36, 36\n"
                    + "Colors at Pixel (3, 3): 213, 213, 213\n",
            view.toString("testImageBlue"));
  }


  @Test
  public void testInvalidBlueCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "blue-component 10 10 testImageBlue quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidBlueCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "blue-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testValueCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "value-component testImage testImageValue quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 147, 147, 147\n"
                    + "Colors at Pixel (0, 1): 185, 185, 185\n"
                    + "Colors at Pixel (0, 2): 203, 203, 203\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 98, 98, 98\n"
                    + "Colors at Pixel (1, 1): 240, 240, 240\n"
                    + "Colors at Pixel (1, 2): 130, 130, 130\n"
                    + "Colors at Pixel (1, 3): 156, 156, 156\n"
                    + "Colors at Pixel (2, 0): 120, 120, 120\n"
                    + "Colors at Pixel (2, 1): 185, 185, 185\n"
                    + "Colors at Pixel (2, 2): 240, 240, 240\n"
                    + "Colors at Pixel (2, 3): 215, 215, 215\n"
                    + "Colors at Pixel (3, 0): 155, 155, 155\n"
                    + "Colors at Pixel (3, 1): 195, 195, 195\n"
                    + "Colors at Pixel (3, 2): 84, 84, 84\n"
                    + "Colors at Pixel (3, 3): 213, 213, 213\n",
            view.toString("testImageValue"));
  }


  @Test
  public void testInvalidValueCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "value-component 10 10 testImageValue quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidValueCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "value-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testIntensityCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "intensity-component testImage testImageIntensity quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 121, 121, 121\n"
                    + "Colors at Pixel (0, 1): 127, 127, 127\n"
                    + "Colors at Pixel (0, 2): 114, 114, 114\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 97, 97, 97\n"
                    + "Colors at Pixel (1, 1): 127, 127, 127\n"
                    + "Colors at Pixel (1, 2): 116, 116, 116\n"
                    + "Colors at Pixel (1, 3): 130, 130, 130\n"
                    + "Colors at Pixel (2, 0): 88, 88, 88\n"
                    + "Colors at Pixel (2, 1): 152, 152, 152\n"
                    + "Colors at Pixel (2, 2): 222, 222, 222\n"
                    + "Colors at Pixel (2, 3): 148, 148, 148\n"
                    + "Colors at Pixel (3, 0): 146, 146, 146\n"
                    + "Colors at Pixel (3, 1): 186, 186, 186\n"
                    + "Colors at Pixel (3, 2): 47, 47, 47\n"
                    + "Colors at Pixel (3, 3): 166, 166, 166\n",
            view.toString("testImageIntensity"));
  }


  @Test
  public void testInvalidIntensityCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "intensity-component 10 10 testImageIntensity quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidIntensityCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "intensity-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testLumaCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "luma-component testImage testImageLuma quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 105, 105, 105\n"
                    + "Colors at Pixel (0, 1): 164, 164, 164\n"
                    + "Colors at Pixel (0, 2): 158, 158, 158\n"
                    + "Colors at Pixel (0, 3): 117, 117, 117\n"
                    + "Colors at Pixel (1, 0): 97, 97, 97\n"
                    + "Colors at Pixel (1, 1): 111, 111, 111\n"
                    + "Colors at Pixel (1, 2): 115, 115, 115\n"
                    + "Colors at Pixel (1, 3): 136, 136, 136\n"
                    + "Colors at Pixel (2, 0): 67, 67, 67\n"
                    + "Colors at Pixel (2, 1): 144, 144, 144\n"
                    + "Colors at Pixel (2, 2): 216, 216, 216\n"
                    + "Colors at Pixel (2, 3): 140, 140, 140\n"
                    + "Colors at Pixel (3, 0): 152, 152, 152\n"
                    + "Colors at Pixel (3, 1): 192, 192, 192\n"
                    + "Colors at Pixel (3, 2): 67, 67, 67\n"
                    + "Colors at Pixel (3, 3): 137, 137, 137\n",
            view.toString("testImageLuma"));
  }


  @Test
  public void testInvalidLumaCommandThird() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "luma-component 10 10 testImageLuma quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testInvalidLumaCommandFourth() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "luma-component 10 testImage 36 quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Invalid input or no more input\n"
            + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n", view.getMessage());
  }

  @Test
  public void testSepiaCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "sepia testImage testImageRed quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 149, 132, 103\n"
                    + "Colors at Pixel (0, 1): 204, 182, 142\n"
                    + "Colors at Pixel (0, 2): 186, 166, 129\n"
                    + "Colors at Pixel (0, 3): 158, 141, 110\n"
                    + "Colors at Pixel (1, 0): 131, 117, 91\n"
                    + "Colors at Pixel (1, 1): 149, 133, 103\n"
                    + "Colors at Pixel (1, 2): 155, 138, 107\n"
                    + "Colors at Pixel (1, 3): 183, 163, 127\n"
                    + "Colors at Pixel (2, 0): 100, 89, 69\n"
                    + "Colors at Pixel (2, 1): 196, 174, 136\n"
                    + "Colors at Pixel (2, 2): 255, 255, 203\n"
                    + "Colors at Pixel (2, 3): 187, 167, 130\n"
                    + "Colors at Pixel (3, 0): 202, 180, 140\n"
                    + "Colors at Pixel (3, 1): 255, 229, 178\n"
                    + "Colors at Pixel (3, 2): 80, 71, 55\n"
                    + "Colors at Pixel (3, 3): 198, 176, 137\n",
            view.toString("testImageRed"));
  }

  @Test
  public void testBlurCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "blur testImage testImageRed quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 58, 67, 74\n"
                    + "Colors at Pixel (0, 1): 62, 113, 96\n"
                    + "Colors at Pixel (0, 2): 58, 120, 91\n"
                    + "Colors at Pixel (0, 3): 58, 79, 65\n"
                    + "Colors at Pixel (1, 0): 67, 80, 104\n"
                    + "Colors at Pixel (1, 1): 86, 136, 160\n"
                    + "Colors at Pixel (1, 2): 103, 152, 157\n"
                    + "Colors at Pixel (1, 3): 90, 108, 105\n"
                    + "Colors at Pixel (2, 0): 80, 84, 108\n"
                    + "Colors at Pixel (2, 1): 117, 139, 168\n"
                    + "Colors at Pixel (2, 2): 126, 152, 176\n"
                    + "Colors at Pixel (2, 3): 93, 109, 133\n"
                    + "Colors at Pixel (3, 0): 79, 79, 83\n"
                    + "Colors at Pixel (3, 1): 102, 114, 111\n"
                    + "Colors at Pixel (3, 2): 88, 106, 113\n"
                    + "Colors at Pixel (3, 3): 67, 73, 100\n",
              view.toString("testImageRed"));
  }

  @Test
  public void testSharpenCommand() {
    Readable in = new StringReader("load images/test_img_4x4.ppm testImage "
            + "sharpen testImage testImageRed quit");
    Appendable log = new StringBuilder();
    IModel model = new ImageDatabase();
    IView view = new ViewImpl(model, log);
    IController c = new ControllerImpl(model, view, in);
    c.run();

    assertEquals("Colors at Pixel (0, 0): 110, 106, 152\n"
                    + "Colors at Pixel (0, 1): 113, 243, 137\n"
                    + "Colors at Pixel (0, 2): 59, 255, 157\n"
                    + "Colors at Pixel (0, 3): 118, 128, 84\n"
                    + "Colors at Pixel (1, 0): 127, 128, 186\n"
                    + "Colors at Pixel (1, 1): 126, 255, 255\n"
                    + "Colors at Pixel (1, 2): 207, 255, 255\n"
                    + "Colors at Pixel (1, 3): 208, 228, 189\n"
                    + "Colors at Pixel (2, 0): 155, 123, 236\n"
                    + "Colors at Pixel (2, 1): 244, 255, 255\n"
                    + "Colors at Pixel (2, 2): 255, 255, 255\n"
                    + "Colors at Pixel (2, 3): 166, 199, 255\n"
                    + "Colors at Pixel (3, 0): 190, 174, 164\n"
                    + "Colors at Pixel (3, 1): 255, 255, 230\n"
                    + "Colors at Pixel (3, 2): 137, 205, 190\n"
                    + "Colors at Pixel (3, 3): 169, 144, 232\n",
            view.toString("testImageRed"));
  }
}
