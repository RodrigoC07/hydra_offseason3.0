package All.Commands.Limelight;

import com.seattlesolvers.solverslib.command.InstantCommand;

import All.Subsystems.Limelight_AprilTag;

public class RecalibratePose extends InstantCommand {
    public RecalibratePose (Limelight_AprilTag limelightAprilTag) {super (limelightAprilTag::recalibratePose, limelightAprilTag);}
}
