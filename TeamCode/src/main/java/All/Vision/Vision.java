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

    public Vision (HardwareMap hwmap) {
        limelight = hwmap.get(Limelight3A.class, "limelight");

        if (BLUE_SIDE) {
            APRIL_TAG_PIPELINE = 0;
        } else {
            APRIL_TAG_PIPELINE = 1;
        }

    }

//    public Pose GetBotPoseMT1 (Pose odometryPose) {
//
//        if (limelight.isRunning()) limelight.start();
//        limelight.pipelineSwitch(APRIL_TAG_PIPELINE);
//        LLResult result = limelight.getLatestResult();
//
//        if (result != null && result.isValid()) {
//            List<LLResultTypes.FiducialResult> aprilTags = result.getFiducialResults();
//
//            for (LLResultTypes.FiducialResult aprilTag : aprilTags) {
//                if (localizationAprilTags.contains(aprilTag.getFiducialId())) {
//
//                }
//            }
//
//        }
//
//    }

}
