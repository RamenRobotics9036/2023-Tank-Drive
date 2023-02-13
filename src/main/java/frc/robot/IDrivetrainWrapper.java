package frc.robot;

public interface IDrivetrainWrapper {
  public void setMaxOutput(double maxOutput);
  public void setDeadband(double deadband);

  // $TODO - Will need to also wrap autonomousPeriodic() when we add simulation support for autonomous
  public void robotPeriodic();
  public void simulationPeriodic();

  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs);
}

