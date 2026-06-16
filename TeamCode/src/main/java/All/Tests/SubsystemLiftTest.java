package All.Tests;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class SubsystemLiftTest extends SubsystemBase {

    // MOTORS
    private final Motor liftMotor;

    // LIFT CONSTANTS
    private int lowPos = 800;
    private int highPos = 1600;

    // PIDF
    private double kP = 0.05;

    public SubsystemLiftTest(HardwareMap hwMap) {

        liftMotor = new Motor(hwMap, "liftMotor");
        liftMotor.setRunMode(Motor.RunMode.PositionControl);
        liftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        liftMotor.setInverted(true);
        liftMotor.setPositionCoefficient(kP);
        liftMotor.setPositionTolerance(15);

    }

    @Override
    public void periodic() {
        liftMotor.set(0);

        while (!liftMotor.atTargetPosition()) {
            liftMotor.set(0.75);
        }

    }

    // COMMANDS
    public void downLift () {
        liftMotor.setTargetPosition(lowPos);
    }

    public void upLift () {
        liftMotor.setTargetPosition(highPos);
    }

    // GET AND SET
    public double liftMotorPosition () { return liftMotor.getCurrentPosition(); }

}