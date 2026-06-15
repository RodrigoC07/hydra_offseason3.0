package All.Tests;

import com.seattlesolvers.solverslib.command.InstantCommand;

public class clawClose extends InstantCommand {
    public clawClose(SubsystemTest subsystemTest) {super (subsystemTest::closeClaw, subsystemTest);}
}
