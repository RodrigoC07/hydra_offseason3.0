package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class clawClose extends InstantCommand {
    public clawClose(SubsystemClawTest subsystemClawTest) {super (subsystemClawTest::closeClaw, subsystemClawTest);}
}
