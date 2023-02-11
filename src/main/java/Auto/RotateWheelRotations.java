package Auto;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RotateWheelRotations {
    private double gearBoxRatio = 8.28;
    private double wheelDiameter = 6;
    private double wheelCircumfrence = wheelDiameter * Math.PI;
    private double wheelRotationToInches = wheelCircumfrence / gearBoxRatio;
    private double percentOutput;

    private DifferentialDrive m_drive;
    private RelativeEncoder m_mainEncoder;

    private double m_targetRotation;
    private boolean isFinished = false;

    public RotateWheelRotations(DifferentialDrive m_drive, RelativeEncoder m_mainEncoder, RelativeEncoder secondaryEncoder, double m_targetRotation, double percentOutput) {
        this.m_drive = m_drive;
        this.m_mainEncoder = m_mainEncoder;
        this.m_targetRotation = m_targetRotation;
        this.percentOutput = percentOutput;
        m_mainEncoder.setPosition(0);
        secondaryEncoder.setPosition(0);
    }

    public void execute() {
        m_drive.tankDrive(percentOutput, percentOutput);
        System.out.println("AUTO COMMAND SCHEDULED IN AUTO CLASS | OUTPUT SET TO: " + percentOutput);
        System.out.println("RIGHT ENCODER POSITION AT " + Math.abs(m_mainEncoder.getPosition()));
    }

    public boolean isFinished() {
        if (Math.abs(m_mainEncoder.getPosition()) >= m_targetRotation * gearBoxRatio) {
                isFinished = true;
        }
        if (!isFinished) {
            System.out.println("AUTO COMMAND CHECKED IN AUTO CLASS | IS FINISHED:" + (isFinished));
        }
        return isFinished;
    }
}