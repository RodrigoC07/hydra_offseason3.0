package All.Subsystems;

import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class Limelight extends SubsystemBase {

    // DRIVE
    private Drive drive;

    // LIMELIGHT
    Limelight3A limelight;

    // LIMELIGHT CONSANTS
    LLResult llResult = limelight.getLatestResult();
    Pose3D robotPose = llResult.getBotpose();
    Pose2D ftc2DPose = new Pose2D(
            DistanceUnit.METER,
            robotPose.getPosition().x,
            robotPose.getPosition().y,
            AngleUnit.RADIANS,
            robotPose.getOrientation().getYaw()
    );

    Pose ftcStandartPose = PoseConverter.pose2DToPose(ftc2DPose, InvertedFTCCoordinates.INSTANCE);
    Pose limelightPose = ftcStandartPose.getAsCoordinateSystem(PedroCoordinates.INSTANCE);

    public Limelight (HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        drive = new Drive(hwMap);
    }

    // COMMANDS
    public void recalibratePose () {
        limelightPose = drive.getRobotPose();
    }

    public void start () {
        limelight.start();
    }

    public void switchPipeline (int pipeline) {
        limelight.pipelineSwitch(pipeline);
    }

}
