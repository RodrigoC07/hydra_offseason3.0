package All.Tests;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class DownAndClose extends SequentialCommandGroup {

    public DownAndClose(SubsystemClawTest subsystemClawTest, SubsystemLiftTest subsystemLiftTest) {

        addCommands(
            new liftDown(subsystemLiftTest),
            new WaitCommand(2000),
            new clawClose(subsystemClawTest)
        );

    }

}
