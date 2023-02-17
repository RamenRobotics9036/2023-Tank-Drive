package Auto;

import DrivetrainWrapper.IDrivetrainWrapper;

public class TurnInPlace {
    private double distancePerTurn = 2.38;

    IDrivetrainWrapper m_driveTrainWrapper;

    private double m_targetRotation;
    private double m_percentOutput;
    private boolean turnLeft;
    private boolean isFinished = false;

    public TurnInPlace(boolean turnLeft, IDrivetrainWrapper driveTrainWrapper, double targetRotation, double percentOutput) {
        this.m_driveTrainWrapper = driveTrainWrapper;
        this.m_targetRotation = targetRotation;
        this.m_percentOutput = percentOutput;
        this.turnLeft =  turnLeft;

        // Reset encoders
        driveTrainWrapper.resetRelativeEncoders();
    }

    public void execute() {
        if (turnLeft) {
            m_driveTrainWrapper.arcadeDrive(0.0, m_percentOutput, true);
        } else {
            m_driveTrainWrapper.arcadeDrive(0.0, -m_percentOutput, true);
        }

        System.out.println("Left wheel distance travelled: " + m_driveTrainWrapper.getLeftRelativeDistance());
    }

    public boolean isFinished() {
        boolean result = false;
    
        if (Math.abs(m_driveTrainWrapper.getLeftRelativeDistance()) >= m_targetRotation * distancePerTurn) {

            // Stop motors
            m_driveTrainWrapper.arcadeDrive(0.0, 0.0, true);
            result = true;
        }

        return result;
    }
}
