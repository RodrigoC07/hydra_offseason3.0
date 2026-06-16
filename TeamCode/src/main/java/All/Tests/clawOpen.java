package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class clawOpen extends InstantCommand {
    public clawOpen(SubsystemClawTest subsystemClawTest) {super (subsystemClawTest::openClaw, subsystemClawTest);}
}
