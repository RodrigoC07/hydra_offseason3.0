package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class liftUp extends InstantCommand {
    public liftUp(SubsystemTest subsystemTest) {super (subsystemTest::upLift, subsystemTest);}
}
