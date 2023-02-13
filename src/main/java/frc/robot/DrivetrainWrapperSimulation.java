package frc.robot;

public class DrivetrainWrapperSimulation implements IDrivetrainWrapper {

  private Drivetrain m_driveTrain;

  // Constructor
  public DrivetrainWrapperSimulation() {
    m_driveTrain = new Drivetrain();
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
}

