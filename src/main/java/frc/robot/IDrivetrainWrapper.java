package frc.robot;

public interface IDrivetrainWrapper {
  public void setMaxOutput(double maxOutput);

  public void robotPeriodic();
  public void simulationPeriodic();

  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs);
}

