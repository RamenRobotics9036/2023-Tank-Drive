package RelativeEncoderWrapper;

import com.revrobotics.RelativeEncoder;

public class RelativeEncoderWrapper implements IRelativeEncoderWrapper {

  private final RelativeEncoder m_relEncoder;
  private final double m_physicalGearBoxRatio;
  private final double m_physicalWheelDiameterMeters;

  // Constructor
  public RelativeEncoderWrapper(RelativeEncoder relEncoder, double physicalGearBoxRatio, double physicalWheelDiameterMeters) {
    m_relEncoder = relEncoder;
    m_physicalGearBoxRatio = physicalGearBoxRatio;
    m_physicalWheelDiameterMeters = physicalWheelDiameterMeters;

    resetPosition();
  }

  public double getPosition() {
    return m_relEncoder.getPosition();
  }

  public void resetPosition() {
    m_relEncoder.setPosition(0);
  }

  public void setPositionConversionFactor(double factor) {
    m_relEncoder.setPositionConversionFactor(factor);
    resetPosition();
  }

  public void setPositionConversionToMeters() {
    // By default, RevRobotics motor returns 1 for a single rotation of the motor
    double newFactor = (m_physicalWheelDiameterMeters * Math.PI) / m_physicalGearBoxRatio;
    setPositionConversionFactor(newFactor);
  }
  
  public double getPositionConversionFactor() {
    return m_relEncoder.getPositionConversionFactor();
  }
}
