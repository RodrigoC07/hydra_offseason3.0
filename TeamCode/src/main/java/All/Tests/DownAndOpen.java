package All.Tests;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;

public class DownAndOpen extends ParallelCommandGroup {

    // SUBSYSTEMS
    private final SubsystemTest subsystemTest;

    // CONSTANTS

    public DownAndOpen (SubsystemTest subsystemTest) {
        this.subsystemTest =  subsystemTest;

        addCommands(

        );

        addRequirements(subsystemTest);
    }

}
