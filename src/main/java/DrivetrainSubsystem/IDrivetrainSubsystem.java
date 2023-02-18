package DrivetrainSubsystem;

public interface IDrivetrainSubsystem {
  public void setMaxOutput(double maxOutput);
  public void setDeadband(double deadband);

  public void robotPeriodic();
  public void simulationPeriodic();

  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs);

  public double getLeftRelativeDistance();
  public double getRightRelativeDistance();
  public void resetRelativeEncoders();
}
