import model.IImageState;
import model.IModel;

/**
 * A mock model to test if the constructor is passing inputs to the model correctly.
 */
public class MockModel implements IModel {
  private StringBuilder log;
  private int code;

  /**
   * This constructors initializes a StringBuilder log and a custom code, both opf which allow
   * for testing the constructors passing of input to the model.
   *
   * @param log a String representing the input that the constructor will feed to the model
   * @param code an int representing a custom code for testing
   */
  public MockModel(StringBuilder log, int code) {
    this.log = log;
    this.code = code;
  }


  @Override
  public void add(String imageId, IImageState image) {
    log.append(imageId + " " + this.code);
  }

  @Override
  public IImageState get(String idImage) {
    return null;
  }
}
