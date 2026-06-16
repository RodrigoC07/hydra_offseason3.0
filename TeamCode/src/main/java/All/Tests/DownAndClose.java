package All.Tests;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class DownAndClose extends ParallelCommandGroup {

    public DownAndClose(SubsystemClawTest subsystemClawTest, SubsystemLiftTest subsystemLiftTest) {

        addCommands(
            new liftDown(subsystemLiftTest),
            new WaitCommand(2000),
            new clawClose(subsystemClawTest)
        );

    }

}
