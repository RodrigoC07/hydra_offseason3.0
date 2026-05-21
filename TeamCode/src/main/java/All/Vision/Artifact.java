package All.Vision;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

public class Artifact {

    // FIELD
    public double x;
    public double y;
    public double z;

    // LIMELIGHT
    public final double fx = 1221.445;
    public final double fy = 1223.398;
    public final double cx = 637.226;
    public final double cy = 502.549;
    public final double fxNN = fx / 4;
    public final double fyNN = fy / 4;
    public final double cxNN = cx / 4;
    public final double cyNN = cy / 4;
    public Pose3D cameraPoseOnRobot = new Pose3D(
            new Position(DistanceUnit.METER, 0.19, 0.107, 0.245, 0),
            new YawPitchRollAngles(AngleUnit.DEGREES, 3, 0.0, 0.0, 0));

    // ARTIFACT
    final double ARTIFACT_RADIUS_INCHES = 2.45;
    public double txBottomCenter;
    public double tyBottomCenter;
    public double txCenter;
    public double tyCenter;
    public List<List<Double>> corners;
    public ARTIFACT_TYPE artifactType;
    public double zDiff;

    public enum ARTIFACT_TYPE {
        GROUND,
        INVALID
    }

    public Artifact(LLResultTypes.DetectorResult detectorResult,
                    Pose botPose) {

        if (detectorResult == null) {
            artifactType = ARTIFACT_TYPE.INVALID;
            return;
        }

        // X AND Y CENTER DEGREES
        this.txCenter = detectorResult.getTargetXDegrees();
        this.tyCenter = detectorResult.getTargetYDegrees();

        // CORNERS
        this.corners = detectorResult.getTargetCorners();

        // TOP Y
        double targetYTopCenter = getTopCenterYPixels(corners);
        double yTopOffset = cyNN - targetYTopCenter;
        double tyTopCenter = Math.toDegrees(Math.atan(yTopOffset / fyNN));

        // X AND Y BOTTOM
        double targetX = getBottomCenterXPixels(corners);
        double targetY = getBottomCenterYPixels(corners);

        // X AND Y OFFSET
        double xOffset = targetX - cxNN;
        double yOffset = cyNN - targetY;

        // X AND Y BOTTOM WITH OFFSET
        this.txBottomCenter = Math.toDegrees(Math.atan(xOffset / fxNN));
        this.tyBottomCenter = Math.toDegrees(Math.atan(yOffset / fyNN));

        // IDENTIFY GROUND ARTIFACT
        if (tyTopCenter < 0){
            this.artifactType = ARTIFACT_TYPE.GROUND;

            double verticalAngleDeg = tyCenter + 90 + cameraPoseOnRobot.getOrientation().getPitch(AngleUnit.DEGREES);

            // X AND Y ARTIFACT RELATIVE TO LIMELIGHT
            double relativeX = ((cameraPoseOnRobot.getPosition().toUnit(DistanceUnit.INCH).z - ARTIFACT_RADIUS_INCHES) * tan(Math.toRadians(verticalAngleDeg)));
            double relativeY = -(relativeX * tan(Math.toRadians(txCenter + cameraPoseOnRobot.getOrientation().getYaw(AngleUnit.DEGREES))));

            relativeX += cameraPoseOnRobot.getPosition().toUnit(DistanceUnit.INCH).x;
            relativeY += cameraPoseOnRobot.getPosition().toUnit(DistanceUnit.INCH).y;

            // BOT POSE
            double botPoseX = botPose.getX();
            double botPoseY = botPose.getY();
            double theta = botPose.getHeading();

            double cos = cos(theta);
            double sin = sin(theta);

            this.x = botPoseX + relativeX * cos - (relativeY) * sin;
            this.y = botPoseY + relativeX * sin + (relativeY) * cos;
            this.z = 0;

            // INVALID ARTIFACT
            if (!(x >= 0 && x <= 144 && y >= 0 && y <= 144)){
                this.artifactType = ARTIFACT_TYPE.INVALID;
            }

        }

    }

    // CALCAS
    public Double getTopCenterYPixels(List<List<Double>> corners) {
        if (corners == null) return null;

        double targetYPixels = 0;

        List<Double> corner0 = corners.get(0);
        List<Double> corner1 = corners.get(1);
        List<Double> corner2 = corners.get(2);
        List<Double> corner3 = corners.get(3);

        return targetYPixels;
    }

    public Double getBottomCenterXPixels(List<List<Double>> corners) {
        if (corners == null) return null;

        double targetXPixels = 0;

        List<Double> corner0 = corners.get(0);
        List<Double> corner1 = corners.get(1);
        List<Double> corner2 = corners.get(2);
        List<Double> corner3 = corners.get(3);

        return targetXPixels;
    }

    public Double getBottomCenterYPixels(List<List<Double>> corners) {
        if (corners == null) return null;

        double targetYPixels = 0;

        List<Double> corner0 = corners.get(0);
        List<Double> corner1 = corners.get(1);
        List<Double> corner2 = corners.get(2);
        List<Double> corner3 = corners.get(3);

        return targetYPixels;
    }
}
