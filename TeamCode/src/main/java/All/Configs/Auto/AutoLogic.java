package All.Configs.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import All.Tests.SubsystemClawTest;
import All.Tests.SubsystemLiftTest;
import All.Tests.UpAndOpen;

public class AutoLogic {

    // SUBSYSTEMS
    private SubsystemClawTest clawSubsystem;
    private SubsystemLiftTest liftSubsystem;

    // CONSTANTS
    public final ElapsedTime timer = new ElapsedTime();
    public enum liftState {
        IDLE,
        UP_OPEN,
        DOWN_CLOSE,
        STOP
    }
    public liftState state = liftState.IDLE;

    public void init (HardwareMap hwMap) {
        liftSubsystem = new SubsystemLiftTest(hwMap);
        clawSubsystem = new SubsystemClawTest(hwMap);

    }

    public void UP_OPEN () {
        state = liftState.UP_OPEN;
        new UpAndOpen(clawSubsystem, liftSubsystem);
    }

    public void DOWN_CLOSE () {
        state = liftState.DOWN_CLOSE;
        new UpAndOpen(clawSubsystem, liftSubsystem);
    }

    public void STOP () {
        state = liftState.STOP;
        stopAll();
    }

    public boolean isBusy () {
        return state != liftState.IDLE;
    }

    public void stopAll () {
        state = liftState.IDLE;
    }

    public void update () {

        switch (state) {

            case IDLE:
                break;

            case UP_OPEN:
                break;

            case DOWN_CLOSE:
                break;

            case STOP:
                break;

        }

    }

}