// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  private final MotorController m_leftMotor = new CANSparkMax(2, MotorType.kBrushless);
  private final MotorController m_rightMotor = new CANSparkMax(1, MotorType.kBrushless);
  private DifferentialDrive m_tankDrive;
  private boolean arcadeDrive = true;

  private XboxController m_controller;

  @Override
  public void robotInit() {
    m_controller = new XboxController(0);
    m_rightMotor.setInverted(true);
    m_tankDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_tankDrive.setMaxOutput(0.2);
  }

  @Override
  public void teleopPeriodic() {
    if (m_controller.getAButtonReleased()) {
      arcadeDrive = !arcadeDrive;
      System.out.println("DRIVE MODE SWITCHED TO " + arcadeDrive);
    }
    if (arcadeDrive == true) {
      m_tankDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getLeftX());
    } else if (arcadeDrive == false) {
      m_tankDrive.tankDrive(-m_controller.getLeftY(), -m_controller.getRightY());
    }
  }
}
