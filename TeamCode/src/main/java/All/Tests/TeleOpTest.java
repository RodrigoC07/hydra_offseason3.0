package All.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp
public class TeleOpTest extends CommandOpMode {

    // SUBSYSTEMS
    private SubsystemClawTest subsystemClawTest;
    private SubsystemLiftTest subsystemLiftTest;

    // GAMEPADS
    private GamepadEx gamepad1Ex;

    @Override
    public void initialize() {

        // GAMEPAD
        gamepad1Ex = new GamepadEx(gamepad1);

        // SUBSYSTEMS
        subsystemClawTest = new SubsystemClawTest(hardwareMap);
        subsystemLiftTest = new SubsystemLiftTest(hardwareMap);

        gamepad1Ex.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new UpAndOpen(subsystemClawTest, subsystemLiftTest));

        gamepad1Ex.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new DownAndClose(subsystemClawTest, subsystemLiftTest));

    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("liftMotor", subsystemLiftTest.liftMotorPosition());
        telemetry.addData("clawServo", subsystemClawTest.clawServoPosition());
        telemetry.update();
    }

}
