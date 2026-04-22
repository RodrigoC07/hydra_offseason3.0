package All.Tests;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class SubsystemTest extends SubsystemBase {

    // MOTORS
    private final Motor liftMotor;

    // SERVOS
    private final Servo clawServo;

    // LIFT CONSTANTS
    private int lowPos = 800;
    private int highPos = 1600;

    // CLAW CONSTANTS
    private double closeClawPos = 0.2;
    private double openClawPos = 0.7;
    // PIDF
    private double kP = 0.05;

    public SubsystemTest (HardwareMap hwMap) {

        liftMotor = hwMap.get(Motor.class, "liftMotor");
        liftMotor.setRunMode(Motor.RunMode.PositionControl);
        liftMotor.setPositionCoefficient(kP);

        clawServo = hwMap.get(Servo.class, "clawServo");

    }

    @Override
    public void periodic() {

    }

    // COMMANDS

    public void downLift () { liftMotor.setTargetPosition(lowPos); }

    public void upLift () { liftMotor.setTargetPosition(highPos); }

    public void closeClaw() { clawServo.setPosition(closeClawPos); }

    public void openClaw() { clawServo.setPosition(openClawPos);}

}