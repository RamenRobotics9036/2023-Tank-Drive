// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private final MotorController m_leftMotor = new CANSparkMax(10, MotorType.kBrushless);
  private final MotorController m_rightMotor = new CANSparkMax(12, MotorType.kBrushless);
  // Change to Talon SRX
  private final MotorController m_armWinchMotor = new CANSparkMax(3, MotorType.kBrushed);
  private DifferentialDrive m_tankDrive;
  private boolean arcadeDrive = true;
  private XboxController m_controller;

  // Controls the gain on the Arm Winch motor (0 to 1)
  public static final String m_armWinchGainKey = "ArmWinchGain";
  private static final double m_armWinchGainDefault = 0.1;
  private static double m_armWinchGainValue = m_armWinchGainDefault;

  @Override
  public void robotInit() {
    m_controller = new XboxController(0);
    m_rightMotor.setInverted(true);
    m_leftMotor.setInverted(true);
    m_tankDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_tankDrive.setMaxOutput(0.3);

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopInit() {
    initRobotPreferences();
  }

  private void initRobotPreferences()
  {
    // Init robot preferences if they don't already exist in flash memory
    if (!Preferences.containsKey(m_armWinchGainKey)) {
      Preferences.setDouble(m_armWinchGainKey, m_armWinchGainDefault);
    }

    m_armWinchGainValue = Preferences.getDouble(m_armWinchGainKey, m_armWinchGainDefault);
    if (m_armWinchGainValue < 0 || m_armWinchGainValue > 1)
    {
      m_armWinchGainValue = m_armWinchGainDefault;
    }
  }

  @Override
  public void teleopPeriodic() {
    if (m_controller.getAButtonReleased()) {
      arcadeDrive = !arcadeDrive;
      System.out.println("DRIVE MODE SWITCHED TO " + arcadeDrive);
    }

    if (arcadeDrive == true) {
      m_tankDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getLeftX(), true);
    } else if (arcadeDrive == false) {
      m_tankDrive.tankDrive(m_controller.getLeftY(), -m_controller.getRightY(), true);
    }

    // X-button turns motor on forward and Y button for reverse
    if (m_controller.getXButtonPressed()) {
      System.out.println("X BUTTON PRESSED");
        // Update to talon
        m_armWinchMotor.set(m_armWinchGainValue);
      } else if (m_controller.getYButtonPressed()) {
        System.out.println("Y BUTTON PRESSED");
        // Update to talon
        m_armWinchMotor.set(-1 * m_armWinchGainValue);
    } else {
      // Update to talon
      m_armWinchMotor.set(0);
    }
  }
}