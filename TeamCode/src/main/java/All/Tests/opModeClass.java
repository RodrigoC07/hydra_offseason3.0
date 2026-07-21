package All.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class opModeClass extends OpMode {

    public DcMotor motor;

    @Override
    public void init() {

        motor = hardwareMap.get(DcMotor.class, "motor");

    }

    @Override
    public void loop() {
        double power = motor.getPower();

        double leftStickY = gamepad1.left_stick_y;

        boolean a = gamepad1.a;

        if (leftStickY > 0.5) {
            motor.setPower(0.5);
        } else if (!a) {
            motor.setPower(0.2);
        } else {
            motor.setPower(0.0);
        }

        telemetry.addLine("INFO MOTOR");

        telemetry.addData("motorPower I", motor.getPower());
        telemetry.addData("motorPower II", power);

        telemetry.addLine("INFO GAMEPAD");

        telemetry.addData("leftStickY", leftStickY);
        telemetry.addData("A", a);

        telemetry.update();

    }

}
