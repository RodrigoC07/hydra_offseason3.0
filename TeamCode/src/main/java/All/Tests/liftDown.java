package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class liftDown extends InstantCommand {
    public liftDown(SubsystemLiftTest subsystemLiftTest) {super (subsystemLiftTest::downLift, subsystemLiftTest);}
}
