import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.IImage;
import model.IPixel;
import model.IPixelState;
import model.PixelImpl;
import model.strategies.BrightnessTransform;
import model.strategies.FilterTransform;
import model.strategies.ITransformation;
import model.IImageState;
import model.IModel;
import model.ImageImpl;
import model.ImageDatabase;
import model.strategies.IntensityTransform;
import model.strategies.KernelTransform;
import model.strategies.ValueTransform;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the functionality of the model given various valid and invalid inputs.
 */
public class ModelTest {
  IModel model;
  IView view;
  IImage testImage;
  List<Integer>[][] pixels1;

  @Before
  public void setUp() {
    pixels1 = new ArrayList[4][4];

    for (int i = 0; i < pixels1.length; i++) {
      for (int j = 0; j < pixels1.length; j++) {
        pixels1[i][j] = new ArrayList<>();
      }
    }

    pixels1[0][0].add(120);
    pixels1[0][0].add(96);
    pixels1[0][0].add(147);

    pixels1[0][1].add(121);
    pixels1[0][1].add(185);
    pixels1[0][1].add(76);

    pixels1[0][2].add(21);
    pixels1[0][2].add(203);
    pixels1[0][2].add(117);

    pixels1[0][3].add(117);
    pixels1[0][3].add(117);
    pixels1[0][3].add(117);

    pixels1[1][0].add(96);
    pixels1[1][0].add(97);
    pixels1[1][0].add(98);

    pixels1[1][1].add(15);
    pixels1[1][1].add(127);
    pixels1[1][1].add(240);

    pixels1[1][2].add(100);
    pixels1[1][2].add(118);
    pixels1[1][2].add(130);

    pixels1[1][3].add(156);
    pixels1[1][3].add(134);
    pixels1[1][3].add(100);

    pixels1[2][0].add(87);
    pixels1[2][0].add(56);
    pixels1[2][0].add(120);

    pixels1[2][1].add(125);
    pixels1[2][1].add(145);
    pixels1[2][1].add(185);

    pixels1[2][2].add(210);
    pixels1[2][2].add(215);
    pixels1[2][2].add(240);

    pixels1[2][3].add(76);
    pixels1[2][3].add(152);
    pixels1[2][3].add(215);

    pixels1[3][0].add(145);
    pixels1[3][0].add(155);
    pixels1[3][0].add(137);

    pixels1[3][1].add(189);
    pixels1[3][1].add(195);
    pixels1[3][1].add(175);

    pixels1[3][2].add(21);
    pixels1[3][2].add(84);
    pixels1[3][2].add(36);

    pixels1[3][3].add(165);
    pixels1[3][3].add(121);
    pixels1[3][3].add(213);

    testImage = new ImageImpl(4, 4);

    for (int i = 0; i < testImage.getHeight(); i++) {
      for (int j = 0; j < testImage.getWidth(); j++) {
        int r = pixels1[i][j].get(0);
        int g = pixels1[i][j].get(1);
        int b = pixels1[i][j].get(2);

        testImage.setPixel(i, j, r, g, b, 0);
      }
    }
  }

  @Test
  public void testPixelImpl() {
    IPixel testPixel = new PixelImpl(34, 27, 86, 0);
    model = new ImageDatabase();
    view = new ViewImpl(model);
    model.add("testImage", testImage);

    assertEquals(34, testPixel.getR());
    assertEquals(27, testPixel.getG());
    assertEquals(86, testPixel.getB());

    testPixel.setR(125);
    testPixel.setG(242);
    testPixel.setB(177);

    assertEquals(125, testPixel.getR());
    assertEquals(242, testPixel.getG());
    assertEquals(177, testPixel.getB());

    testPixel.setR(500);
    testPixel.setG(-500);
    testPixel.setB(700);

    assertEquals(255, testPixel.getR());
    assertEquals(0, testPixel.getG());
    assertEquals(255, testPixel.getB());
  }

  @Test
  public void testImageImpl() {
    assertEquals(4, testImage.getHeight());
    assertEquals(4, testImage.getWidth());

    assertEquals(120, testImage.getRedChannel(0, 0));
    assertEquals(96, testImage.getGreenChannel(0, 0));
    assertEquals(147, testImage.getBlueChannel(0, 0));

    testImage.setPixel(0, 0, -127, 34, 852, 0);

    assertEquals(0, testImage.getRedChannel(0, 0));
    assertEquals(34, testImage.getGreenChannel(0, 0));
    assertEquals(255, testImage.getBlueChannel(0, 0));

    testImage.setPixel(0, 0, 120, 96, 147, 0);

    IPixelState[][] pixels = testImage.getData();

    assertEquals(120, pixels[0][0].getR());
    assertEquals(96, pixels[0][0].getG());
    assertEquals(147, pixels[0][0].getB());
  }

  @Test
  public void testModelAdd() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    model.add("testImage", testImage);
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
            this.view.toString("testImage"));
  }

  @Test
  public void testModelGet() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    model.add("testImage", testImage);
    IImageState getImage = model.get("testImage");
    model.add("getImage", getImage);

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
            this.view.toString("getImage"));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidModelGet() {
    model = new ImageDatabase();
    model.add("testImage", testImage);
    IImageState getImage = model.get("imageFive");
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidModelAdd() {
    IImageState imageFive = null;
    model = new ImageDatabase();
    model.add("testImage", imageFive);
  }

  @Test
  public void testDecBrightTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation brightness = new BrightnessTransform(-1);
    IImageState newImage = brightness.apply(testImage);

    model.add("testImageDark", newImage);

    assertEquals("Colors at Pixel (0, 0): 119, 95, 146\n"
            + "Colors at Pixel (0, 1): 120, 184, 75\n"
            + "Colors at Pixel (0, 2): 20, 202, 116\n"
            + "Colors at Pixel (0, 3): 116, 116, 116\n"
            + "Colors at Pixel (1, 0): 95, 96, 97\n"
            + "Colors at Pixel (1, 1): 14, 126, 239\n"
            + "Colors at Pixel (1, 2): 99, 117, 129\n"
            + "Colors at Pixel (1, 3): 155, 133, 99\n"
            + "Colors at Pixel (2, 0): 86, 55, 119\n"
            + "Colors at Pixel (2, 1): 124, 144, 184\n"
            + "Colors at Pixel (2, 2): 209, 214, 239\n"
            + "Colors at Pixel (2, 3): 75, 151, 214\n"
            + "Colors at Pixel (3, 0): 144, 154, 136\n"
            + "Colors at Pixel (3, 1): 188, 194, 174\n"
            + "Colors at Pixel (3, 2): 20, 83, 35\n"
            + "Colors at Pixel (3, 3): 164, 120, 212\n", this.view.toString("testImageDark"));
  }

  @Test
  public void testIncBrightTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation brightness = new BrightnessTransform(10);
    IImageState newImage = brightness.apply(testImage);

    model.add("testImageBright", newImage);

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
            this.view.toString("testImageBright"));
  }

  @Test
  public void testClampedIncBrightTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation brightness = new BrightnessTransform(100);
    IImageState newImage = brightness.apply(testImage);
    model.add("testImageBrightClamp", newImage);

    assertEquals("Colors at Pixel (0, 0): 220, 196, 247\n"
                    + "Colors at Pixel (0, 1): 221, 255, 176\n"
                    + "Colors at Pixel (0, 2): 121, 255, 217\n"
                    + "Colors at Pixel (0, 3): 217, 217, 217\n"
                    + "Colors at Pixel (1, 0): 196, 197, 198\n"
                    + "Colors at Pixel (1, 1): 115, 227, 255\n"
                    + "Colors at Pixel (1, 2): 200, 218, 230\n"
                    + "Colors at Pixel (1, 3): 255, 234, 200\n"
                    + "Colors at Pixel (2, 0): 187, 156, 220\n"
                    + "Colors at Pixel (2, 1): 225, 245, 255\n"
                    + "Colors at Pixel (2, 2): 255, 255, 255\n"
                    + "Colors at Pixel (2, 3): 176, 252, 255\n"
                    + "Colors at Pixel (3, 0): 245, 255, 237\n"
                    + "Colors at Pixel (3, 1): 255, 255, 255\n"
                    + "Colors at Pixel (3, 2): 121, 184, 136\n"
                    + "Colors at Pixel (3, 3): 255, 221, 255\n",
            this.view.toString("testImageBrightClamp"));
  }

  @Test
  public void testClampedDecBrightTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation brightness = new BrightnessTransform(-100);
    IImageState newImage = brightness.apply(testImage);
    model.add("testImageDarkClamp", newImage);

    assertEquals("Colors at Pixel (0, 0): 20, 0, 47\n"
                    + "Colors at Pixel (0, 1): 21, 85, 0\n"
                    + "Colors at Pixel (0, 2): 0, 103, 17\n"
                    + "Colors at Pixel (0, 3): 17, 17, 17\n"
                    + "Colors at Pixel (1, 0): 0, 0, 0\n"
                    + "Colors at Pixel (1, 1): 0, 27, 140\n"
                    + "Colors at Pixel (1, 2): 0, 18, 30\n"
                    + "Colors at Pixel (1, 3): 56, 34, 0\n"
                    + "Colors at Pixel (2, 0): 0, 0, 20\n"
                    + "Colors at Pixel (2, 1): 25, 45, 85\n"
                    + "Colors at Pixel (2, 2): 110, 115, 140\n"
                    + "Colors at Pixel (2, 3): 0, 52, 115\n"
                    + "Colors at Pixel (3, 0): 45, 55, 37\n"
                    + "Colors at Pixel (3, 1): 89, 95, 75\n"
                    + "Colors at Pixel (3, 2): 0, 0, 0\n"
                    + "Colors at Pixel (3, 3): 65, 21, 113\n",
            this.view.toString("testImageDarkClamp"));
  }

  @Test
  public void testRedComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    double[][] matrix = new double[][] {
            {1d, 0d, 0d},
            {1d, 0d, 0d},
            {1d, 0d, 0d}
    };
    ITransformation filterTransform = new FilterTransform(matrix);
    IImageState newImage = filterTransform.apply(testImage);
    model.add("testImageRed", newImage);

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
            this.view.toString("testImageRed"));
  }

  @Test
  public void testGreenComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    model.add("testImage", testImage);
    double[][] matrix = new double[][] {
            {0d, 1d, 0d},
            {0d, 1d, 0d},
            {0d, 1d, 0d}
    };
    ITransformation filterTransform = new FilterTransform(matrix);
    IImageState newImage = filterTransform.apply(testImage);
    model.add("testImageGreen", newImage);

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
            this.view.toString("testImageGreen"));
  }

  @Test
  public void testBlueComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    model.add("testImage", testImage);
    double[][] matrix = new double[][]{
            {0d, 0d, 1d},
            {0d, 0d, 1d},
            {0d, 0d, 1d}
    };
    ITransformation filterTransform = new FilterTransform(matrix);
    IImageState newImage = filterTransform.apply(testImage);
    model.add("testImageBlue", newImage);

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
            this.view.toString("testImageBlue"));
  }

  @Test
  public void testValueComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation valueTransform = new ValueTransform();
    IImageState newImage = valueTransform.apply(testImage);
    model.add("testImageValue", newImage);

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
            this.view.toString("testImageValue"));
  }

  @Test
  public void testIntensityComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    ITransformation intensityTransform = new IntensityTransform();
    IImageState newImage = intensityTransform.apply(testImage);
    model.add("testImageIntensity", newImage);

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
            this.view.toString("testImageIntensity"));
  }

  @Test
  public void testLumaComponentTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    double[][] matrix = new double[][] {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };

    ITransformation filterTransform = new FilterTransform(matrix);
    IImageState newImage = filterTransform.apply(testImage);
    model.add("testImageLuma", newImage);

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
            this.view.toString("testImageLuma"));
  }

  @Test
  public void testBlurTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    double[][] kernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    ITransformation kernelTransform = new KernelTransform(kernel);
    IImageState newImage = kernelTransform.apply(testImage);
    model.add("testImageBlur", newImage);

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
            this.view.toString("testImageBlur"));
  }

  @Test
  public void testSharpenTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    double[][] kernel = new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    ITransformation kernelTransform = new KernelTransform(kernel);
    IImageState newImage = kernelTransform.apply(testImage);
    model.add("testImageSharpen", newImage);

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
            this.view.toString("testImageSharpen"));
  }

  @Test
  public void testSepiaTransform() {
    model = new ImageDatabase();
    view = new ViewImpl(model);
    double[][] matrix = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    ITransformation filterTransform = new FilterTransform(matrix);
    IImageState newImage = filterTransform.apply(testImage);
    model.add("testImageRed", newImage);

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
            this.view.toString("testImageRed"));
  }
}
