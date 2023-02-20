package DrivetrainWrapper;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.RelativeEncoder;
import RelativeEncoderWrapper.IRelativeEncoderWrapper;
import RelativeEncoderWrapper.RelativeEncoderWrapper;


public class DrivetrainWrapper implements IDrivetrainWrapper {
  private final CANSparkMax m_leftMotor1;
  private final CANSparkMax m_leftMotor2;
  private final CANSparkMax m_rightMotor1;
  private final CANSparkMax m_rightMotor2;

  private final MotorControllerGroup m_leftMotor;
  private final MotorControllerGroup m_rightMotor;

  private final IRelativeEncoderWrapper m_leftEncoderWrapper;
  private final IRelativeEncoderWrapper m_rightEncoderWrapper;

  private DifferentialDrive m_DifferentialDrive;

  // Constructor
  public DrivetrainWrapper(double physicalGearBoxRatio, double physicalWheelDiameterMeters) {
    m_leftMotor1  = new CANSparkMax(10, MotorType.kBrushless);
    m_leftMotor2  = new CANSparkMax(11, MotorType.kBrushless);
    m_rightMotor1 = new CANSparkMax(12, MotorType.kBrushless);
    m_rightMotor2 = new CANSparkMax(13, MotorType.kBrushless);

    m_leftMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);
    m_rightMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);

    m_leftMotor   = new MotorControllerGroup(m_leftMotor1, m_leftMotor2);
    m_rightMotor  = new MotorControllerGroup(m_rightMotor1, m_rightMotor2);

    m_rightMotor.setInverted(true);

    m_DifferentialDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_leftEncoderWrapper = new RelativeEncoderWrapper(m_leftMotor1.getEncoder(), physicalGearBoxRatio, physicalWheelDiameterMeters);
    m_rightEncoderWrapper = new RelativeEncoderWrapper(m_rightMotor1.getEncoder(), physicalGearBoxRatio, physicalWheelDiameterMeters);

    m_leftEncoderWrapper.resetPosition();
    m_rightEncoderWrapper.resetPosition();
  }

  // Factory
  //
  // NOTE: We take the robot's PHYSICAL gearbox ratio, and physical wheel dimesions.  Why?  Because by default our RelativeSimulatedEncoder will return one rotation of the motor.
  //   We want that to translate to the same physical distance (in meters) for both the simulation and the real robot.  Hence, we require the physical GearBoxRatio
  //   and the physical WheelDiameterMeters
  //
  public static IDrivetrainWrapper CreateDrivetrainWrapper(boolean isSimulation, double physicalGearBoxRatio, double physicalWheelDiameterMeters) {
    IDrivetrainWrapper result = isSimulation ?
      new DrivetrainWrapperSimulation(physicalGearBoxRatio, physicalWheelDiameterMeters) :
      new DrivetrainWrapper(physicalGearBoxRatio, physicalWheelDiameterMeters);

    System.out.println("Creating: " + result.toString());

    return result;
  }

  public void setMaxOutput(double maxOutput) {
    m_DifferentialDrive.setMaxOutput(maxOutput);
  }

  public void setDeadband(double deadband) {
    m_DifferentialDrive.setDeadband(deadband);
  }

  public void robotPeriodic() {
    // No op
  }

  public void simulationPeriodic() {
    // No op
  }
  
  public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs) {
    m_DifferentialDrive.arcadeDrive(xSpeed, zRotation, squareInputs);
  }

  public IRelativeEncoderWrapper getLeftEncoder() {
    return m_leftEncoderWrapper;
  }
  
  public IRelativeEncoderWrapper getRightEncoder() {
    return m_rightEncoderWrapper;
  }
}

