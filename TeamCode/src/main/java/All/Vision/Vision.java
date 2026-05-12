package All.Vision;

import static java.lang.Double.NaN;

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
    public Pose3D cameraPoseOnRobot = new Pose3D(
    new Position(DistanceUnit.METER, 0.19, 0.107,0.245,0),
    new YawPitchRollAngles(AngleUnit.DEGREES, 3, 0.0, 0.0, 0));

    // APRIL TAGS
    public boolean BLUE_SIDE = true;
    public int APRIL_TAG_PIPELINE = 0;
    public int ARTIFACT_PIPELINE = 3;
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

        if (!limelight.isRunning()) limelight.start();
        limelight.pipelineSwitch(APRIL_TAG_PIPELINE);
        LLResult result = limelight.getLatestResult();

        double odometryHeading = odometryPose.getHeading();
        Pose out = null;

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> aprilTags = result.getFiducialResults();

            for (LLResultTypes.FiducialResult aprilTag : aprilTags) {
                if (localizationAprilTags.contains(aprilTag.getFiducialId())) {

                    Pose aprilTagPose = getLimelightToPedroPose(result.getBotpose());
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

    public List<List<Artifact>> getArtifacts (Pose PedroPose) {

        if (!limelight.isRunning()) limelight.start();
        limelight.pipelineSwitch(ARTIFACT_PIPELINE);
        LLResult result = limelight.getLatestResult();

        List<Artifact> groundArtifacts = new ArrayList<>();

        if (result == null || !result.isValid()) {
            List<List<Artifact>> out = new ArrayList<>();
            out.add(groundArtifacts);
            return out;
        }


        for (LLResultTypes.DetectorResult detectorResult : result.getDetectorResults()) {
            Artifact artifact = new Artifact(detectorResult, PedroPose);

            if (artifact.artifactType == Artifact.ARTIFACT_TYPE.GROUND) {
                groundArtifacts.add(artifact);
            }

        }

            List<List<Artifact>> out = new ArrayList<>();
            out.add(groundArtifacts);

            return out;
        }

//        public Double intakeAngleToArtifact (List<Artifact> artifacts, Pose botPose, int stepDeg) {
//            if (artifacts.isEmpty()) return null;
//
//            double bestAngle = NaN;
//            final double MAX_RELEVANT_DISTANCE = 70;
//
//
//        }

    // CALCS
    public Pose getLimelightToPedroPose (Pose3D llPose) {

        double llX = llPose.getPosition().toUnit(DistanceUnit.INCH).x;
        double llY = llPose.getPosition().toUnit(DistanceUnit.INCH).y;
        double llYam = llPose.getOrientation().getYaw(AngleUnit.RADIANS);

        double xTransformed = llY + 72.04;
        double yTransformed = 72.03 - llX;
        double yamTransformed = Math.abs(llYam);

        return new Pose(xTransformed, yTransformed, yamTransformed);
    }

}
