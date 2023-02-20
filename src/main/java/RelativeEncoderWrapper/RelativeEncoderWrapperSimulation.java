package RelativeEncoderWrapper;

import edu.wpi.first.wpilibj.Encoder;

public class RelativeEncoderWrapperSimulation implements IRelativeEncoderWrapper {
  private final Encoder m_encoder;
  private final double m_physicalGearBoxRatio;
  private final double m_physicalWheelDiameterMeters;
  private double m_offset;
  private double m_conversionFactor;

  // Constructor
  public RelativeEncoderWrapperSimulation(Encoder encoder, double physicalGearBoxRatio, double physicalWheelDiameterMeters) {
    m_encoder = encoder;
    m_physicalGearBoxRatio = physicalGearBoxRatio;
    m_physicalWheelDiameterMeters = physicalWheelDiameterMeters;    
    m_offset = 0;
    m_conversionFactor = 1.0;

    resetPosition();
  }

  public double getPosition(){
    double unscaledDistance = m_encoder.getDistance() - m_offset;

    // First, we have to conver the distance that the SIMULATED motor moved into # of motor ROTATIONS.
    // Note that we do this based on the PHYSICAL robot's gearbox ratio, so that a single turn of the motor
    // will move both the simulated and physical robots the same distance
    double motorRotations = unscaledDistance * m_physicalGearBoxRatio / (m_physicalWheelDiameterMeters * Math.PI);

    // And now we can take into account the conversionFactor that caller asked us to use
    return motorRotations * m_conversionFactor;
  }
  
  public void resetPosition() {
    m_offset = m_encoder.getDistance();
  }
  
  public void setPositionConversionFactor(double factor) {
    m_conversionFactor = factor;
  }
  
  public void setPositionConversionToMeters() {
    m_conversionFactor = (m_physicalWheelDiameterMeters * Math.PI) / m_physicalGearBoxRatio;
  }
  
  public double getPositionConversionFactor() {
    return m_conversionFactor;
  }
}
