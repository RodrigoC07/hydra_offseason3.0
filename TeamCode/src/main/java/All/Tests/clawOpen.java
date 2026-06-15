package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class clawOpen extends InstantCommand {
    public clawOpen(SubsystemTest subsystemTest) {super (subsystemTest::openClaw, subsystemTest);}
}
