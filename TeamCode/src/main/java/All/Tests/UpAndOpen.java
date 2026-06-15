package All.Tests;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class UpAndOpen extends ParallelCommandGroup {

    // SUBSYSTEMS
    private final SubsystemTest subsystemTest;

    // CONSTANTS

    public UpAndOpen(SubsystemTest subsystemTest) {
        this.subsystemTest =  subsystemTest;

        addCommands(
            new liftUp(subsystemTest),
            new WaitCommand(2000),
            new clawOpen(subsystemTest)
        );

        addRequirements(subsystemTest);
    }

}
