package All.OpModes;

import static All.Configs.Poses.FieldPoses.BLUE_GOAL;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import All.Commands.Drive.DriveCommand;
import All.Commands.Drive.ResetFieldCentric;
import All.Commands.Limelight.RecalibratePose;
import All.Subsystems.Drive;
import All.Subsystems.Limelight_AprilTag;
import All.Subsystems.Turret;
import All.Vision.Vision;

@TeleOp
public class MainTeleOp extends CommandOpMode {

    // SUBSYSTEMS
    private Drive drive;
    private Limelight_AprilTag limelightAprilTag;
    private Turret turret;

    // GAMEPADS
    private GamepadEx gamepad1Ex;

    @Override
    public void initialize() {

        // GAMEPAD
        gamepad1Ex = new GamepadEx(gamepad1);

        // DRIVE
        drive = new Drive(hardwareMap);
        drive.setStartPose(new Pose(33, 111, Math.toRadians(180)));
        drive.setDefaultCommand(new DriveCommand(drive, gamepad1Ex));

        gamepad1Ex.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ResetFieldCentric(drive));

        // LIMELIGHT
        limelightAprilTag = new Limelight_AprilTag(hardwareMap, drive);

        // TURRET
        turret = new Turret(hardwareMap);
    }

    @Override
    public void run() {

        super.run();

        // DRIVE
        drive.updatePinpoint();
        driveWorking();

        // LIMELIGHT
        limelightAprilTagWorking();

        // TURRET
        turret.turretFollowPose(BLUE_GOAL, drive.getRobotPose(), drive.getRobotHeadingRad());

        // TELEMETRY
        telemetry.addData("Odometry Pose", drive.getRobotPose());
        telemetry.addData("Limelight Pose", limelightAprilTag.getLimelightPose());
        telemetry.update();
    }

    public void driveWorking () {

    }

    public void limelightAprilTagWorking () {

    }

}
