// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
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

  private Joystick m_leftStick;
  private Joystick m_rightStick;

  @Override
  public void robotInit() {
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(0);
    m_tankDrive = new DifferentialDrive(m_leftMotor, m_leftMotor);
  }

  @Override
  public void teleopPeriodic() {
    m_tankDrive.tankDrive(m_leftStick.getY(), m_rightStick.getY());
  }
}
