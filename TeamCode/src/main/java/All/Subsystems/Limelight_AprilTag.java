package All.Subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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
    Pose odometryPose = drive.getRobotPose();
    Pose limelightPose = vision.GetBotPoseMT1(odometryPose);

    public Limelight_AprilTag (HardwareMap hwMap) {

        vision = new Vision(hwMap,vision.cameraPoseOnRobot, vision.cameraOrientation);
        drive = new Drive(hwMap);

        vision.BLUE_SIDE = true;
    }

    @Override
    public void periodic() {

    }

    // COMMANDS
    public void recalibratePose () { limelightPose = odometryPose; }

}
