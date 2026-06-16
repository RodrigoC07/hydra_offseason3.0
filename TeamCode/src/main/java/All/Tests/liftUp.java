package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class liftUp extends InstantCommand {
    public liftUp(SubsystemLiftTest subsystemLiftTest) {super (subsystemLiftTest::upLift, subsystemLiftTest);}
}
