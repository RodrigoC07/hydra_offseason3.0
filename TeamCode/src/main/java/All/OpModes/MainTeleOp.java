package All.OpModes;

import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import All.Commands.Drive.DriveCommand;
import All.Commands.Drive.ResetFieldCentric;
import All.Commands.Limelight.RecalibratePose;
import All.Subsystems.Drive;
import All.Subsystems.Limelight_AprilTag;
import All.Vision.Vision;

public class MainTeleOp extends CommandOpMode {

    // SUBSYSTEMS
    private Drive drive;
    private Limelight_AprilTag limelightAprilTag;
    // private Turret turret;

    // CLASSES
    private Vision vision;

    // GAMEPADS
    private GamepadEx gamepad1;

    @Override
    public void initialize() {
        // DRIVE
        drive = new Drive(hardwareMap);

        Pose startPose = new Pose(33, 111, Math.toRadians(180));
        drive.setStartPose(startPose);
        drive.updatePinpoint();
        drive.setDefaultCommand(new DriveCommand(drive, gamepad1));

        gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ResetFieldCentric(drive));

        // LIMELIGHT
        limelightAprilTag = new Limelight_AprilTag(hardwareMap);

        // TURRET
        // turret = new Turret(hardwareMap);

        // VISION
        vision = new Vision(hardwareMap, vision.cameraPoseOnRobot, vision.cameraOrientation);
    }

    @Override
    public void run() {

        super.run();

        waitForStart();

        // DRIVE
        drive.updatePinpoint();
        driveWorking();

        // LIMELIGHT
        limelightAprilTagWorking();

        // TURRET
        // turret.turretFollowPose(BLUE_GOAL, drive.getRobotPose(), drive.getRobotHeadingRad());

        // TELEMETRY
        telemetry.addData("Odometry Pose :", drive.getRobotPose());
        telemetry.addData("LimelightPose", vision.GetBotPoseMT1(drive.getRobotPose()));
        telemetry.update();
    }

    public void driveWorking () {
+
        gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ResetFieldCentric(drive));

    }

    public void limelightAprilTagWorking () {

        gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new RecalibratePose(limelightAprilTag));

    }

}
