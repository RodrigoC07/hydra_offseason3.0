package All.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servoTest extends LinearOpMode {

    public Servo servo;
    public double pos1 = 0.0;
    public static double pos2 = 1.0;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, "clawServo");

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                servo.setPosition(pos1);
            } else if (gamepad1.b) {
                servo.setPosition(pos2);
            }

            telemetry.addData("motor power", servo.getPosition());
            telemetry.update();
        }

    }

}
