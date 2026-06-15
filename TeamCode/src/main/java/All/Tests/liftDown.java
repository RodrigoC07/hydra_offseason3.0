package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class liftDown extends InstantCommand {
    public liftDown(SubsystemTest subsystemTest) {super (subsystemTest::downLift, subsystemTest);}
}
