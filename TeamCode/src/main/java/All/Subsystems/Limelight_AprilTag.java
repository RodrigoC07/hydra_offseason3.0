package All.Subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import All.Vision.Vision;

public class Limelight_AprilTag extends SubsystemBase {

    Vision vision;
    Drive drive;
    Pose odometryPose;
    Pose limelightPose;

    public Limelight_AprilTag (HardwareMap hwMap, Drive drive) {

        this.drive = drive;

        vision = new Vision(hwMap,
        new Pose3D(
        new Position(DistanceUnit.METER, 0.19, 0.107,0.245,0),
        new YawPitchRollAngles(AngleUnit.DEGREES, 3, 0.0, 0.0, 0)));

        vision.BLUE_SIDE = true;
        odometryPose = drive.getRobotPose();
        limelightPose = vision.GetBotPoseMT1(odometryPose);

    }

    @Override
    public void periodic() {

        odometryPose = drive.getRobotPose();
        limelightPose = vision.GetBotPoseMT1(odometryPose);

    }

    // COMMANDS
    public void recalibratePose () { odometryPose = limelightPose; }

    // GET AND SET
    public Pose getLimelightPose() {
        return limelightPose;
    }

}
