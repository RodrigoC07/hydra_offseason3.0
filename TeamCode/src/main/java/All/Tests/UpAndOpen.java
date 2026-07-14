package All.Tests;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

public class UpAndOpen extends SequentialCommandGroup {

    public UpAndOpen(SubsystemClawTest subsystemClawTest, SubsystemLiftTest subsystemLiftTest) {

        addCommands(
            new liftUp(subsystemLiftTest),
            new WaitCommand(2000),
            new clawOpen(subsystemClawTest)
        );

    }

}
