package Auto;

import DrivetrainSubsystem.IDrivetrainSubsystem;

public class TurnInPlace {
    private double distancePerTurn = 2.38;

    IDrivetrainSubsystem m_driveTrainSubsystem;

    private double m_targetRotation;
    private double m_percentOutput;
    private boolean turnLeft;
    private boolean isFinished = false;

    public TurnInPlace(boolean turnLeft, IDrivetrainSubsystem driveTrainSubsystem, double targetRotation, double percentOutput) {
        this.m_driveTrainSubsystem = driveTrainSubsystem;
        this.m_targetRotation = targetRotation;
        this.m_percentOutput = percentOutput;
        this.turnLeft =  turnLeft;

        // Reset encoders
        driveTrainSubsystem.resetRelativeEncoders();
    }

    public void execute() {
        if (turnLeft) {
            m_driveTrainSubsystem.arcadeDrive(0.0, m_percentOutput, true);
        } else {
            m_driveTrainSubsystem.arcadeDrive(0.0, -m_percentOutput, true);
        }

        System.out.println("Left wheel distance travelled: " + m_driveTrainSubsystem.getLeftRelativeDistance());
    }

    public boolean isFinished() {
        boolean result = false;
    
        if (Math.abs(m_driveTrainSubsystem.getLeftRelativeDistance()) >= m_targetRotation * distancePerTurn) {

            // Stop motors
            m_driveTrainSubsystem.arcadeDrive(0.0, 0.0, true);
            result = true;
        }

        return result;
    }
}
