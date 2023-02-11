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

import Auto.RotateWheelRotations;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController; 
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

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

  private final CANSparkMax m_leftMotor1 = new CANSparkMax(10, MotorType.kBrushless);
  private final CANSparkMax m_leftMotor2 = new CANSparkMax(11, MotorType.kBrushless);
  private final CANSparkMax m_rightMotor1 = new CANSparkMax(12, MotorType.kBrushless);
  private final CANSparkMax m_rightMotor2 = new CANSparkMax(13, MotorType.kBrushless);

  private final MotorControllerGroup m_leftMotor = new MotorControllerGroup(m_leftMotor1, m_leftMotor2);
  private final MotorControllerGroup m_rightMotor = new MotorControllerGroup(m_rightMotor1, m_rightMotor2);
  private DifferentialDrive m_tankDrive;

  private Joystick m_joystick;
  private XboxController m_controller;

  private RelativeEncoder m_leftMotorEncoder;
  private RelativeEncoder m_rightMotorEncoder;

  public static final String m_exampleKey = "m_exampleKey";
  private static final double m_exampleDefaultValue = 0.5;
  private static double m_exampleValue = m_exampleDefaultValue;

  private RotateWheelRotations autoCommand;


  @Override
  public void robotInit() {
    m_leftMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);
    m_rightMotor1.setIdleMode(IdleMode.kBrake);
    m_rightMotor2.setIdleMode(IdleMode.kBrake);
    m_joystick = new Joystick(1);

    m_controller = new XboxController(0);
    m_rightMotor.setInverted(true);

    // m_pneumaticHub = new PneumaticHub();

    // m_compressor.enableDigital();
    // m_leftArmSolenoid = m_pneumaticHub.makeDoubleSolenoid(0, 1);
    // m_rightArmSolenoid = m_pneumaticHub.makeDoubleSolenoid(2, 3);

    m_tankDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    // m_tankDrive.setMaxOutput(0.35);

    m_rightMotorEncoder = m_leftMotor1.getEncoder();
    m_leftMotorEncoder = m_rightMotor1.getEncoder();

    // CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopInit() {
    m_rightMotorEncoder.setPosition(0);
    m_leftMotorEncoder.setPosition(0);
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
  public void teleopPeriodic() {
    m_tankDrive.arcadeDrive(-m_joystick.getY(), -m_joystick.getX(), true);
    // System.out.println("LEFT MOTOR SPEED: " + m_leftMotor1.get());
    // System.out.println("RIGHT MOTOR SPEED: " + m_rightMotor1.get());
    // System.out.println("JOYSTICK Y AXIS: " + m_joystick.getY());

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

    // if (m_controller.getLeftY() > 0.1 || m_controller.getLeftY() < 0.1) {
    //   m_armExtensionMotor.set(VictorSPXControlMode.PercentOutput, -m_controller.getLeftY());
    //   System.out.println("LEFT JOYSTICK PRESSED | OUTPUT SET TO " + m_armExtensionMotor.getMotorOutputPercent());
    // } else if (m_controller.getRightY() > 0.1 || m_controller.getRightY() < 0.1) {
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
    System.out.println("LEFT MOTOR POSITION AT " + m_leftMotorEncoder.getPosition());
    System.out.println("RIGHT MOTOR POSITION AT " + m_rightMotorEncoder.getPosition());
  }

  @Override
  public void autonomousInit() {
    autoCommand = new RotateWheelRotations(m_tankDrive, m_rightMotorEncoder, m_leftMotorEncoder, 10, 0.4);
    System.out.println("AUTO COMMAND INITIATED IN ROBOT CLASS");
  }

  @Override
  public void autonomousPeriodic() {
    if (!autoCommand.isFinished()) {
      autoCommand.execute();
      System.out.println("AUTO COMMAND SCHEDULED IN ROBOT CLASS");
    }
  }
}