package All.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class motorTest extends LinearOpMode {

    public DcMotor motor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "liftMotor");

        waitForStart();
        while (opModeIsActive()) {

            motor.setPower(gamepad1.left_stick_y);

            telemetry.addData("motor power", motor.getPower());
            telemetry.update();
        }

    }

}
