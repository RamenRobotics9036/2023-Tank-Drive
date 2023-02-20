package DrivetrainWrapper;

import RelativeEncoderWrapper.IRelativeEncoderWrapper;
import RelativeEncoderWrapper.RelativeEncoderWrapperSimulation;

public class DrivetrainWrapperSimulation implements IDrivetrainWrapper {

  private Drivetrain m_driveTrain;
  private IRelativeEncoderWrapper m_leftEncoderWrapper;
  private IRelativeEncoderWrapper m_rightEncoderWrapper;

  // Constructor
  public DrivetrainWrapperSimulation(double physicalGearBoxRatio, double physicalWheelDiameterMeters) {
    m_driveTrain = new Drivetrain();

    m_leftEncoderWrapper = new RelativeEncoderWrapperSimulation(m_driveTrain.getLeftEncoder(), physicalGearBoxRatio, physicalWheelDiameterMeters);
    m_rightEncoderWrapper = new RelativeEncoderWrapperSimulation(m_driveTrain.getRightEncoder(), physicalGearBoxRatio, physicalWheelDiameterMeters);

    m_leftEncoderWrapper.resetPosition();
    m_rightEncoderWrapper.resetPosition();
  }

  public void setMaxOutput(double maxOutput) {
    m_driveTrain.setMaxOutput(maxOutput);
  }

  public void setDeadband(double deadband) {
    m_driveTrain.setDeadband(deadband);
  }
  
  public void robotPeriodic() {
    m_driveTrain.periodic();
  }

  public void simulationPeriodic() {
    m_driveTrain.simulationPeriodic();
  }
  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs) {
    m_driveTrain.arcadeDrive(xSpeed, zRotation, squareInputs);
  }

  public IRelativeEncoderWrapper getLeftEncoder() {
    return m_leftEncoderWrapper;
  }

  public IRelativeEncoderWrapper getRightEncoder() {
    return m_rightEncoderWrapper;
  }
}

