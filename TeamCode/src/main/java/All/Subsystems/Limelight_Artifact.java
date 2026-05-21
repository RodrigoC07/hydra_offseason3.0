package All.Subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import All.Vision.Artifact;
import All.Vision.Vision;

public class Limelight_Artifact extends SubsystemBase {

    Vision vision;
    Drive drive;
    Artifact artifact;
    Double intakingAngle;
    Pose botPose;

    public Limelight_Artifact (HardwareMap hwMap, Drive drive) {

        this.drive = drive;

        vision = new Vision(hwMap,
                new Pose3D(
                new Position(DistanceUnit.METER, 0.19, 0.107,0.245,0),
                new YawPitchRollAngles(AngleUnit.DEGREES, 3, 0.0, 0.0, 0)));

        botPose = drive.getRobotPose();

        intakingAngle = vision.intakeAngleToArtifact(vision.getArtifacts(botPose).get(0),botPose, 180);
    }

    // GET AND SET
    public Double getIntakingAngle() {return intakingAngle;}

}
