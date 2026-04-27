package All.Vision;

import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.ArrayList;
import java.util.List;

public class Vision {

    // LIMELIGHT
    public Limelight3A limelight;
    public Pose3D cameraPoseOnRobot = new Pose3D(
    new Position(DistanceUnit.METER, 0.19, 0.107,0.245,0),
    new YawPitchRollAngles(AngleUnit.DEGREES, 3, 0.0, 0.0, 0));

    // APRIL TAGS
    public boolean BLUE_SIDE = true;
    public int APRIL_TAG_PIPELINE = 0;
    List<Integer> localizationAprilTags = new ArrayList<>();

    // NEURAL DETECTION
    public int NEURAL_PIPELINE = 3;

    public Vision (HardwareMap hwmap,Pose3D cameraPoseOnRobot) {
        limelight = hwmap.get(Limelight3A.class, "limelight");
        this.cameraPoseOnRobot = cameraPoseOnRobot;
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

                    Pose aprilTagPose = getLimelightToPedroPose(result);
                    double aprilTagHeading = aprilTagPose.getHeading();

                    double diffDeg = (aprilTagHeading - odometryHeading + 540) % 360 - 180;
                    double diffRad = Math.toRadians(diffDeg);
                    double headingDiff = Math.abs(diffRad);

                    double dist = Math.hypot(aprilTagPose.getX() - odometryPose.getX(), aprilTagPose.getY() - odometryPose.getY());

                    out = aprilTagPose;

                }
            }
        }
        else return null;

        return out;
    }


    // CALCS
    public Pose getLimelightToPedroPose (LLResult result) {

        Pose3D megaTagPose = result.getBotpose();

        Pose2D ftc2DStandartPose = new Pose2D(
            DistanceUnit.INCH,
            -megaTagPose.getPosition().x,
            megaTagPose.getPosition().y,
            AngleUnit.RADIANS,
            megaTagPose.getOrientation().getYaw()
        );

        Pose ftcStandartPose = PoseConverter.pose2DToPose(ftc2DStandartPose, InvertedFTCCoordinates.INSTANCE);

        Pose out =
            ftcStandartPose.getAsCoordinateSystem(
                    PedroCoordinates.INSTANCE
            );

        return out;
    }

    public double limelightToStandardYaw(double llYawDegrees){
        double yawTransformed = (llYawDegrees + 270) % 360;

        if (yawTransformed < 0) {
            yawTransformed += 360;
        }
        return yawTransformed;
    }

}
