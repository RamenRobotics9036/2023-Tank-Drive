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

import Auto.RotateLeftMotor;
import Auto.RotateRightMotor;
import Auto.RotateWheelRotations;
import Auto.TurnInPlace;

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

  private Joystick m_joystick;
  private XboxController m_controller;

  public static final String m_exampleKey = "m_exampleKey";
  private static final double m_exampleDefaultValue = 0.5;
  private static double m_exampleValue = m_exampleDefaultValue;

  private RotateWheelRotations autoCommandRotate;
  private RotateRightMotor autoCommandRight;
  private RotateLeftMotor autoCommandLeft;
  private TurnInPlace autoCommandTurnInPlace;


  @Override
  public void robotInit() {
    m_driveTrainWrapper = DrivetrainWrapper.CreateDrivetrainWrapper(this.isSimulation());
    m_driveTrainWrapper.setMaxOutput(0.2);
    m_driveTrainWrapper.setDeadband(0.1);
  
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
    m_driveTrainWrapper.resetRelativeEncoders();

    // Settings are reloaded each time robot switches back to teleop mode
    initRobotPreferences();
  }

  @Override
  public void autonomousInit() {
    System.out.println("Starting autonomous...");
    m_driveTrainWrapper.resetRelativeEncoders();
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
  public void autonomousPeriodic() {
    double xSpeed = 0;
    double zRotation = 0;
  
    // Drive two meters and stop
    double distanceTravelled = m_driveTrainWrapper.getLeftRelativeDistance();
    if (distanceTravelled < 2) {
      xSpeed = 0.7;

      System.out.println("Distance travelled: " + distanceTravelled);
    }

    m_driveTrainWrapper.arcadeDrive(xSpeed, zRotation, true);
  }

  @Override
  public void disabledInit() {
    System.out.println("ROBOT DISABLED");
  }

  @Override
  public void autonomousInit() {
    autoCommandTurnInPlace = new TurnInPlace(true, m_tankDrive, m_rightMotorEncoder, m_leftMotorEncoder, 5, 0.4);
    System.out.println("AUTO COMMAND SCHEDULED");
  }

  @Override
  public void autonomousPeriodic() {
    if (!autoCommandTurnInPlace.isFinished()) {
      autoCommandTurnInPlace.execute();
    }
  }
}
