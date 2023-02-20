// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController; 
import DrivetrainWrapper.IDrivetrainWrapper;
import DrivetrainWrapper.DrivetrainWrapper;
import RelativeEncoderWrapper.IRelativeEncoderWrapper;
import Auto.TurnInPlace;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  // private final TalonSRX m_armWinchMotor = new TalonSRX(20);
  // private final VictorSPX m_armExtensionMotor = new VictorSPX(19);

  // private final Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);
  // private DoubleSolenoid m_leftArmSolenoid;
  // // private DoubleSolenoid m_rightArmSolenoid;\
  // private PneumaticHub m_pneumaticHub;

  private IDrivetrainWrapper m_driveTrainWrapper;
  private IRelativeEncoderWrapper m_leftEncoderWrapper;
  private IRelativeEncoderWrapper m_rightEncoderWrapper;

  private Joystick m_joystick;
  private XboxController m_controller;

  public static final String m_exampleKey = "m_exampleKey";
  private static final double m_exampleDefaultValue = 0.5;
  private static double m_exampleValue = m_exampleDefaultValue;

  private TurnInPlace m_autoCommandTurnInPlace;


  @Override
  public void robotInit() {
    double gearBoxRatio = 8.28;
    double wheelDiameterMeters = 0.1524; // 6 inches

    m_driveTrainWrapper = DrivetrainWrapper.CreateDrivetrainWrapper(
      Robot.isSimulation(),
      gearBoxRatio,
      wheelDiameterMeters);

    m_driveTrainWrapper.setMaxOutput(0.2);
    m_driveTrainWrapper.setDeadband(0.1);

    m_leftEncoderWrapper = m_driveTrainWrapper.getLeftEncoder();
    m_rightEncoderWrapper = m_driveTrainWrapper.getRightEncoder();
    m_leftEncoderWrapper.setPositionConversionToMeters();
    m_rightEncoderWrapper.setPositionConversionToMeters();
    
    m_joystick = new Joystick(1);
    m_controller = new XboxController(0);

    // m_pneumaticHub = new PneumaticHub();

    // m_compressor.enableDigital();
    // m_leftArmSolenoid = m_pneumaticHub.makeDoubleSolenoid(0, 1);
    // m_rightArmSolenoid = m_pneumaticHub.makeDoubleSolenoid(2, 3);

    // CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopInit() {
    m_leftEncoderWrapper.resetPosition();
    m_rightEncoderWrapper.resetPosition();

    // Settings are reloaded each time robot switches back to teleop mode
    initRobotPreferences();
  }

  private void initRobotPreferences() {
    // Init robot preferences if they don't already exist in flash memory
    if (!Preferences.containsKey(m_exampleKey)) {
      Preferences.setDouble(m_exampleKey, m_exampleDefaultValue);
    }

    m_exampleValue = Preferences.getDouble(m_exampleKey, m_exampleDefaultValue);
    if (m_exampleValue < 0 || m_exampleValue > 1)
    {
      m_exampleValue = m_exampleDefaultValue;
    }
  }

  @Override
  public void robotPeriodic() {
      m_driveTrainWrapper.robotPeriodic();
  }

  @Override
  public void simulationPeriodic() {
      m_driveTrainWrapper.simulationPeriodic();
  }

  @Override
  public void teleopPeriodic() {
    m_driveTrainWrapper.arcadeDrive(-m_joystick.getY(), -m_joystick.getX(), true);

    // // Right Trigger turns motor on forward and Left Trigger for reverse
    // if (m_controller.getRightTriggerAxis() > 0) {
    //   m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, -Math.pow(m_controller.getRightTriggerAxis(), 2));
    //   System.out.println("RIGHT TRIGGER PRESSED | OUTPUT SET TO " + m_armWinchMotor.getMotorOutputPercent());
    // } else if (m_controller.getLeftTriggerAxis() > 0) {
    //     m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, Math.pow(m_controller.getLeftTriggerAxis(), 2));
    //     System.out.println("LEFT TRIGGER PRESSED | OUTPUT SET TO " + m_armWinchMotor.getMotorOutputPercent());
    // } else {
    //   m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, 0);
    // }

    // // Left Bumper sets solenoid to forward and Left Bumper sets solenoid to reverse
    // if (m_controller.getRightBumperPressed()){
    //   m_leftArmSolenoid.set(DoubleSolenoid.Value.kForward);
    //   // m_rightArmSolenoid.set(DoubleSolenoid.Value.kForward);
    //   System.out.println("LEFT BUMPER PRESSED | LEFT SOLENOID OUTPUT SET TO " + m_leftArmSolenoid.get());
    //   // System.out.println("LEFT BUMPER PRESSED | RIGHT SOLENOID OUTPUT SET TO " + m_rightArmSolenoid.get());
    // } else if (m_controller.getLeftBumperPressed()){
    //     m_leftArmSolenoid.set(DoubleSolenoid.Value.kReverse);
    //     // m_rightArmSolenoid.set(DoubleSolenoid.Value.kReverse);
    //     System.out.println("LEFT BUMPER PRESSED | LEFT SOLENOID OUTPUT SET TO " + m_leftArmSolenoid.get());
    //     // System.out.println("LEFT BUMPER PRESSED | RIGHT SOLENOID OUTPUT SET TO " + m_rightArmSolenoid.get());
    // } else {
    //     // m_leftArmSolenoid.set(DoubleSolenoid.Value.kOff);
    //     // m_rightArmSolenoid.set(DoubleSolenoid.Value.kOff);
    // }

    // if (m_controller.getLeftY() > 0.1 || m_controller.getLeftY() < -0.1) {
    //   m_armExtensionMotor.set(VictorSPXControlMode.PercentOutput, -m_controller.getLeftY());
    //   System.out.println("LEFT JOYSTICK PRESSED | OUTPUT SET TO " + m_armExtensionMotor.getMotorOutputPercent());
    // } else if (m_controller.getRightY() > 0.1 || m_controller.getRightY() < -0.1) {
    //     m_armExtensionMotor.set(VictorSPXControlMode.PercentOutput, -m_controller.getRightY());
    //     System.out.println("RIGHT JOYSTICK PRESSED | OUTPUT SET TO " + m_armExtensionMotor.getMotorOutputPercent());
    // } else {
    //   m_armExtensionMotor.set(VictorSPXControlMode.PercentOutput, 0);
    // }

    // // TODO: Flip joysticks and triggers for drive team
  }

  @Override
  public void disabledInit() {
    System.out.println("ROBOT DISABLED");
  }

  @Override
  public void autonomousInit() {

    System.out.println("Starting autonomous...");

    m_leftEncoderWrapper.resetPosition();
    m_rightEncoderWrapper.resetPosition();

    m_autoCommandTurnInPlace = new TurnInPlace(
      true,
      m_driveTrainWrapper,
      1, // targetRotation
      0.7); // percentOutput

    System.out.println("AUTO COMMAND SCHEDULED");
  }

  @Override
  public void autonomousPeriodic() {
    if (!m_autoCommandTurnInPlace.isFinished()) {
      m_autoCommandTurnInPlace.execute();
    }
  }
}
