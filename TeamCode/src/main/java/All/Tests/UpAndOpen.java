package All.Tests;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class UpAndOpen extends ParallelCommandGroup {

    public UpAndOpen(SubsystemClawTest subsystemClawTest, SubsystemLiftTest subsystemLiftTest) {

        addCommands(
            new liftUp(subsystemLiftTest),
            new WaitCommand(2000),
            new clawOpen(subsystemClawTest)
        );

    }

}
