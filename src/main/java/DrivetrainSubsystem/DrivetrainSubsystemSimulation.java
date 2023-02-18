package DrivetrainSubsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystemSimulation extends SubsystemBase implements IDrivetrainSubsystem {

  private Drivetrain m_driveTrain;

  // Constructor
  public DrivetrainSubsystemSimulation() {
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

  public double getLeftRelativeDistance() {
    return m_driveTrain.getLeftRelativeDistance();
  }

  public double getRightRelativeDistance() {
    return m_driveTrain.getRightRelativeDistance();
  }

  public void resetRelativeEncoders() {
    m_driveTrain.resetRelativeEncoders();
  }
}

