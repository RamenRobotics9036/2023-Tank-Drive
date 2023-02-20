package RelativeEncoderWrapper;

public interface IRelativeEncoderWrapper {
  public double getPosition();
  public void resetPosition();
  public void setPositionConversionFactor(double factor);
  public void setPositionConversionToMeters();
  public double getPositionConversionFactor();
}
