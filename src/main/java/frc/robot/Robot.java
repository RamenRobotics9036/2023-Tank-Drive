// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private final TalonSRX m_armWinchMotor = new TalonSRX(20);

  private final Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);
  private PneumaticHub PHub = new PneumaticHub();
  private DoubleSolenoid m_solenoid;

  private final MotorController m_leftMotor = new CANSparkMax(10, MotorType.kBrushless);
  private final MotorController m_rightMotor = new CANSparkMax(12, MotorType.kBrushless);
  private DifferentialDrive m_tankDrive;
  private XboxController m_controller;

  @Override
  public void robotInit() {
    initRobotPreferences();
    m_compressor.enableDigital();
    m_controller = new XboxController(0);

    m_rightMotor.setInverted(true);
    m_leftMotor.setInverted(true);
    m_tankDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_tankDrive.setMaxOutput(0.1);

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopInit() {
    initRobotPreferences();
    m_solenoid = PHub.makeDoubleSolenoid(0, 1);
  }

  private void initRobotPreferences() {
  }

  @Override
  public void teleopPeriodic() {
    m_tankDrive.tankDrive(m_controller.getLeftY(), -m_controller.getRightY(), true);

    // Right Trigger turns motor on forward and Left Trigger for reverse
    if (m_controller.getRightTriggerAxis() > 0) {
      m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, m_controller.getRightTriggerAxis());
      System.out.println("RIGHT TRIGGER PRESSED | OUTPUT SET TO " + m_armWinchMotor.getMotorOutputPercent());
    } else if (m_controller.getLeftTriggerAxis() > 0) {
        m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, m_controller.getLeftTriggerAxis());
        System.out.println("LEFT TRIGGER PRESSED | OUTPUT SET TO " + m_armWinchMotor.getMotorOutputPercent());
    } else {
      m_armWinchMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }

    // Left Bumper sets solenoid to forward and Left Bumper sets solenoid to reverse
    if (m_controller.getRightBumperPressed()){
      m_solenoid.set(DoubleSolenoid.Value.kForward);
      System.out.println("RIGHT BUMPER PRESSED | OUTPUT SET TO " + m_solenoid.get());
    } else if (m_controller.getLeftBumperPressed()){
        m_solenoid.set(DoubleSolenoid.Value.kReverse);
        System.out.println("LEFT BUMPER PRESSED | OUTPUT SET TO " + m_solenoid.get());
    } else {
        // m_solenoid.set(DoubleSolenoid.Value.kOff);
    }
  }
}