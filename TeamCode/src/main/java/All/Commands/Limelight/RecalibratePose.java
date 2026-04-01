package All.Commands.Limelight;

import com.seattlesolvers.solverslib.command.InstantCommand;

import All.Subsystems.Limelight;

public class RecalibratePose extends InstantCommand {

    public RecalibratePose (Limelight limelight) {super (limelight::recalibratePose, limelight);}

}
