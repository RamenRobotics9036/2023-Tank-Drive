package DrivetrainWrapper;

import RelativeEncoderWrapper.IRelativeEncoderWrapper;

public interface IDrivetrainWrapper {
  public void setMaxOutput(double maxOutput);
  public void setDeadband(double deadband);

  public void robotPeriodic();
  public void simulationPeriodic();

  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs);

  public IRelativeEncoderWrapper getLeftEncoder();
  public IRelativeEncoderWrapper getRightEncoder();
}
