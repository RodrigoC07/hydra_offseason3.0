package All.Configs.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.InstantCommand;

import All.Tests.SubsystemClawTest;
import All.Tests.SubsystemLiftTest;
import All.Tests.UpAndOpen;

public class AutoLogic {

    // SUBSYSTEMS
    private SubsystemClawTest clawSubsystem;
    private SubsystemLiftTest liftSubsystem;

    public enum SubsystemsStates {
        IDLE,
        UP_AND_OPEN,
        DOWN_AND_CLOSE,
        STOP
    }

    public SubsystemsStates subsystemsStates;

    public void init (HardwareMap hwMap) {

        liftSubsystem = new SubsystemLiftTest(hwMap);
        clawSubsystem = new SubsystemClawTest(hwMap);

        subsystemsStates = SubsystemsStates.IDLE;

    }

    public boolean isBusy () {
        return subsystemsStates != SubsystemsStates.IDLE;
    }

    public InstantCommand up_open () {
        return new InstantCommand(() -> {new UpAndOpen(clawSubsystem, liftSubsystem);});
    }

    public InstantCommand down_close () {
        return new InstantCommand(() -> {new UpAndOpen(clawSubsystem, liftSubsystem);});
    }

    public void updateAutoLogic () {

        switch (subsystemsStates) {

            case IDLE:
                if (!isBusy()) {
                    subsystemsStates = SubsystemsStates.UP_AND_OPEN;
                }
                break;

            case UP_AND_OPEN:
                up_open();
                break;

            case DOWN_AND_CLOSE:
                down_close();
                break;

            case STOP:
                break;

        }

    }

}