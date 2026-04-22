package All.Vision;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.ArrayList;
import java.util.List;

public class Vision {

    // LIMELIGHT
    public Limelight3A limelight;
    public CAMERA_ORIENTATION cameraOrientation = CAMERA_ORIENTATION.NORMAL;
    public Pose3D cameraPoseOnRobot = new Pose3D(
    new Position(DistanceUnit.METER, 0.0, 0.0,0.0,0),
    new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0));

    // APRIL TAGS
    public boolean BLUE_SIDE = true;
    public int APRIL_TAG_PIPELINE = 0;
    List<Integer> localizationAprilTags = new ArrayList<>();

    // NEURAL DETECTION
    public int NEURAL_PIPELINE = 3;

    public enum CAMERA_ORIENTATION {
        NORMAL,
        UPSIDE_DOWN,
    }

    public Vision (HardwareMap hwmap,Pose3D cameraPoseOnRobot, CAMERA_ORIENTATION cameraOrientation) {
        limelight = hwmap.get(Limelight3A.class, "limelight");
        this.cameraPoseOnRobot = cameraPoseOnRobot;
        this.cameraOrientation = cameraOrientation;
        localizationAprilTags.add(20);
        localizationAprilTags.add(24);

        if (BLUE_SIDE) {
            APRIL_TAG_PIPELINE = 0;
        } else {
            APRIL_TAG_PIPELINE = 1;
        }

    }

    public Pose GetBotPoseMT1 (Pose odometryPose) {

        if (limelight.isRunning()) limelight.start();
        limelight.pipelineSwitch(APRIL_TAG_PIPELINE);
        LLResult result = limelight.getLatestResult();

        double odometryHeading = odometryPose.getHeading();
        Pose out = null;

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> aprilTags = result.getFiducialResults();

            for (LLResultTypes.FiducialResult aprilTag : aprilTags) {
                if (localizationAprilTags.contains(aprilTag.getFiducialId())) {

                    Pose aprilTagPose = limelightToPedroPose(result.getBotpose());
                    double aprilTagHeading = aprilTagPose.getHeading();

                    double diffDeg = (aprilTagHeading - odometryHeading + 540) % 360 - 180;
                    double diffRad = Math.toRadians(diffDeg);
                    double headingDiff = Math.abs(diffRad);

                    double dist = Math.hypot(aprilTagPose.getX() - aprilTagPose.getY(), aprilTagPose.getY() - aprilTagPose.getX());

                    out = aprilTagPose;

                }
            }
        }
        else return null;

        return out;
    }


    // CALCS
    public Pose limelightToPedroPose(Pose3D llPose) {
        double llX = llPose.getPosition().toUnit(DistanceUnit.INCH).x;
        double llY = llPose.getPosition().toUnit(DistanceUnit.INCH).y;
        double llYaw = llPose.getOrientation().getYaw(AngleUnit.RADIANS);

        double xTransformed = llY + 72;
        double yTransformed = 72 - llX;
        double yawTransformedDeg = limelightToStandardYaw(llYaw);
        double yamTransformedRad = Math.toRadians(yawTransformedDeg);

        return new Pose(xTransformed, yTransformed, yamTransformedRad);
    }

    public double limelightToStandardYaw(double llYawDegrees){
        double yawTransformed = (llYawDegrees + 270) % 360;

        if (yawTransformed < 0) {
            yawTransformed += 360;
        }
        return yawTransformed;
    }

}
