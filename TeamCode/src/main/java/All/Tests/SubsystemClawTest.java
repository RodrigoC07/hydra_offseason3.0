package All.Tests;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class SubsystemClawTest extends SubsystemBase {

    // SERVOS
    private final Servo clawServo;

    // CLAW CONSTANTS
    private double closeClawPos = 0.2;
    private double openClawPos = 0.7;

    public SubsystemClawTest(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "clawServo");
    }

    @Override
    public void periodic() {

    }

    public void closeClaw() { clawServo.setPosition(closeClawPos); }

    public void openClaw() { clawServo.setPosition(openClawPos);}

    // GET AND SET

    public double clawServoPosition () { return clawServo.getPosition(); }

}