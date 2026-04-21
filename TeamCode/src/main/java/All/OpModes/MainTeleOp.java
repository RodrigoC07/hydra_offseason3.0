package All.OpModes;

import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import All.Commands.Drive.DriveCommand;
import All.Commands.Drive.ResetFieldCentric;
import All.Commands.Limelight.RecalibratePose;
import All.Subsystems.Drive;

public class MainTeleOp extends CommandOpMode {

    // SUBSYSTEMS
    private Drive drive;
    // private Turret turret;

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

        // TURRET
        // turret = new Turret(hardwareMap);
    }

    @Override
    public void run() {

        super.run();

        waitForStart();

        // DRIVE
        drive.updatePinpoint();
        driveWorking();

        // TURRET
        // turret.turretFollowPose(BLUE_GOAL, drive.getRobotPose(), drive.getRobotHeadingRad());

    }

    public void driveWorking () {

        gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ResetFieldCentric(drive));

    }

}
