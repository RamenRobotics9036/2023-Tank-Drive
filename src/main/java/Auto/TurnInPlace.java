package Auto;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TurnInPlace {
    private double gearBoxRatio = 8.28;
    // private double wheelDiameter = 6;
    // private double wheelCircumfrence = wheelDiameter * Math.PI;
    // private double wheelRotationToInches = wheelCircumfrence / gearBoxRatio;
    private double percentOutput;

    private DifferentialDrive m_drive;
    private RelativeEncoder m_leftEncoder;
    private RelativeEncoder m_rightEncoder;

    private double m_targetRotation;
    private boolean turnLeft;
    private boolean isFinished = false;

    public TurnInPlace(boolean turnLeft, DifferentialDrive m_drive, RelativeEncoder m_leftEncoder, RelativeEncoder m_rightEncoder, double m_targetRotation, double percentOutput) {
        this.m_drive = m_drive;
        this.m_leftEncoder = m_leftEncoder;
        this.m_rightEncoder = m_rightEncoder;
        this.m_targetRotation = m_targetRotation;
        this.percentOutput = percentOutput;
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        this.turnLeft =  turnLeft;
    }

    public void execute() {
        if (turnLeft) {
            m_drive.tankDrive(percentOutput, -percentOutput);
        } else {
            m_drive.tankDrive(-percentOutput, percentOutput);
        }
        // System.out.println("AUTO COMMAND SCHEDULED IN IN TURN IN PLACE CLASS");
        System.out.println("LEFT ENCODER POSITION AT " + Math.abs(m_leftEncoder.getPosition()));
        System.out.println("RIGHT ENCODER POSITION AT " + Math.abs(m_rightEncoder.getPosition()));
    }

    public boolean isFinished() {
        if ((Math.abs(m_leftEncoder.getPosition()) >= m_targetRotation * gearBoxRatio) && (Math.abs(m_rightEncoder.getPosition()) >= m_targetRotation * gearBoxRatio)) {
            isFinished = true;
        }
        if (!isFinished) {
            // System.out.println("AUTO COMMAND CHECKED IN TURN IN PLACE | IS FINISHED:" + (isFinished));
        }
        return isFinished;
    }
}
