package Auto;

import DrivetrainWrapper.IDrivetrainWrapper;
import RelativeEncoderWrapper.IRelativeEncoderWrapper;

public class TurnInPlace {
    private double distancePerTurn = 2.38;

    private final IDrivetrainWrapper m_driveTrainWrapper;
    private final IRelativeEncoderWrapper m_leftEncoderWrapper;
    private final IRelativeEncoderWrapper m_rightEncoderWrapper;

    private double m_targetRotation;
    private double m_percentOutput;
    private boolean turnLeft;
    private boolean isFinished = false;

    public TurnInPlace(boolean turnLeft, IDrivetrainWrapper driveTrainWrapper, double targetRotation, double percentOutput) {
        this.m_driveTrainWrapper = driveTrainWrapper;
        this.m_leftEncoderWrapper = m_driveTrainWrapper.getLeftEncoder();
        this.m_rightEncoderWrapper = m_driveTrainWrapper.getRightEncoder();

        this.m_targetRotation = targetRotation;
        this.m_percentOutput = percentOutput;
        this.turnLeft =  turnLeft;

        // Reset encoders
        m_leftEncoderWrapper.resetPosition();
        m_rightEncoderWrapper.resetPosition();
    }

    public void execute() {
        if (turnLeft) {
            m_driveTrainWrapper.arcadeDrive(0.0, m_percentOutput, true);
        } else {
            m_driveTrainWrapper.arcadeDrive(0.0, -m_percentOutput, true);
        }

        System.out.println("Left wheel distance travelled: " + m_leftEncoderWrapper.getPosition());
    }

    public boolean isFinished() {
        boolean result = false;
    
        if (Math.abs(m_leftEncoderWrapper.getPosition()) >= m_targetRotation * distancePerTurn) {

          // Stop motors
          m_driveTrainWrapper.arcadeDrive(0.0, 0.0, true);
          result = true;
        }

        return result;
    }
}
