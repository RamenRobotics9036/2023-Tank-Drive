package DrivetrainWrapper;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.RelativeEncoder;

public class DrivetrainWrapper implements IDrivetrainWrapper {
  // Wheel diameter is in meters (6 inches)
  private static final double kWheelDiameter = 0.1524;

  private final CANSparkMax m_leftMotor1;
  private final CANSparkMax m_leftMotor2;
  private final CANSparkMax m_rightMotor1;
  private final CANSparkMax m_rightMotor2;

  private final MotorControllerGroup m_leftMotor;
  private final MotorControllerGroup m_rightMotor;

  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  private DifferentialDrive m_tankDrive;

  // Constructor
  public DrivetrainWrapper() {
    m_leftMotor1  = new CANSparkMax(10, MotorType.kBrushless);
    m_leftMotor2  = new CANSparkMax(11, MotorType.kBrushless);
    m_rightMotor1 = new CANSparkMax(12, MotorType.kBrushless);
    m_rightMotor2 = new CANSparkMax(13, MotorType.kBrushless);

    m_leftMotor   = new MotorControllerGroup(m_leftMotor1, m_leftMotor2);
    m_rightMotor  = new MotorControllerGroup(m_rightMotor1, m_rightMotor2);

    m_leftMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);
    m_rightMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);

    m_rightMotor.setInverted(true);

    m_tankDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_leftEncoder = m_leftMotor1.getEncoder();
    m_rightEncoder = m_rightMotor1.getEncoder();

    // $TODO David pointed out that gear-ratio isn't accounted for here
    m_leftEncoder.setPositionConversionFactor(Math.PI * kWheelDiameter);
    m_rightEncoder.setPositionConversionFactor(Math.PI * kWheelDiameter);
    resetRelativeEncoders();
  }

  // Factory
  public static IDrivetrainWrapper CreateDrivetrainWrapper(boolean isSimulation) {
    IDrivetrainWrapper result = isSimulation ?
      new DrivetrainWrapperSimulation() :
      new DrivetrainWrapper();

    System.out.println("Creating: " + result.toString());

    return result;
  }

  public void setMaxOutput(double maxOutput) {
    m_tankDrive.setMaxOutput(maxOutput);
  }

  public void setDeadband(double deadband) {
    m_tankDrive.setDeadband(deadband);
  }

  public void robotPeriodic() {
    // No op
  }

  public void simulationPeriodic() {
    // No op
  }
  
  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs) {
    m_tankDrive.arcadeDrive(xSpeed, zRotation, squareInputs);
  }

  public double getLeftRelativeDistance() {
    return m_leftEncoder.getPosition();
  }

  public double getRightRelativeDistance() {
    return m_rightEncoder.getPosition();
  }

  public void resetRelativeEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }
}

