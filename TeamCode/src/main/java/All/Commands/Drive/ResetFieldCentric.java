package All.Commands.Drive;

import com.seattlesolvers.solverslib.command.InstantCommand;

import All.Subsystems.Drive;

public class ResetFieldCentric extends InstantCommand {
    public ResetFieldCentric(Drive drive) {super (drive::resetFieldOrietation, drive);}
}
