package All.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp
public class TeleOpTest extends CommandOpMode {

    // SUBSYSTEMS
    private SubsystemTest subsystemTest;

    // GAMEPADS
    private GamepadEx gamepad1Ex;

    @Override
    public void initialize() {

        // GAMEPAD
        gamepad1Ex = new GamepadEx(gamepad1);

        // SUBSYSTEMS
        subsystemTest = new SubsystemTest(hardwareMap);

        gamepad1Ex.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new UpAndOpen(subsystemTest));

        gamepad1Ex.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new DownAndClose(subsystemTest));

    }

    @Override
    public void run() {

        telemetry.addData("liftMotor", subsystemTest.liftMotorPosition());
        telemetry.addData("clawServo", subsystemTest.clawServoPosition());
    }
}
