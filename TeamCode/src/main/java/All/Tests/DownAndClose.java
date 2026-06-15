package All.Tests;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class DownAndClose extends ParallelCommandGroup {

    // SUBSYSTEMS
    private final SubsystemTest subsystemTest;

    // CONSTANTS

    public DownAndClose(SubsystemTest subsystemTest) {
        this.subsystemTest =  subsystemTest;

        addCommands(
                new liftDown(subsystemTest),
                new WaitCommand(2000),
                new clawClose(subsystemTest)
        );

        addRequirements(subsystemTest);
    }

}
