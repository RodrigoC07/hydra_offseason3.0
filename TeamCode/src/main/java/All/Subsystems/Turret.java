package All.Subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Turret extends SubsystemBase {

    // MOTOR
    private DcMotorEx turretMotor;

    // MOTOR CONSTANTS
    private double motorCurrentTicks = turretMotor.getCurrentPosition();
    private double TICKS_PER_REV = 537.7;
    private double motorTicksError = 0;

    // TURRET CONSTANTS
    private double GEAR_RATIO = 3.906976744186047;
    private double MAX_ANGLE = Math.toRadians(90);
    private double turretTargetRad = 0.0;
    private double relocalizationAngleOffset = 0.0;

    // PIDF CONSTANTS
    private final PIDFController pidf;

    public static double kP = 0.003;
    public static double kD = 0.0001;
    public static double kF = 0.0;

    public Turret (HardwareMap hwMap) {
        turretMotor = hwMap.get(DcMotorEx.class, "turret");

        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turretMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        turretMotor.setPower(0);

        pidf = new PIDFController(new PIDFCoefficients(kP, 0, kD, kF));

    }

    @Override
    public void periodic() {
        pidf.setCoefficients(new PIDFCoefficients(kP, 0, kD, kF));
    }

    // TURRET FOLLOWER
    public void turretFollowPose (Pose fieldTarget, Pose robotPose, double robotHeading) {

        double distanceX = fieldTarget.getX() - robotPose.getX();
        double distanceY = fieldTarget.getY() - robotPose.getY();

        turretTargetRad = Math.atan2(distanceY, distanceX);
    }

    // UPDATE TURRET
    private void updateTurret (double robotHeading) {

        double desiredRelative = wrap(turretTargetRad - robotHeading + relocalizationAngleOffset);
        desiredRelative = clamp(desiredRelative);

        double motorTargetTicks = radToTicks(desiredRelative);

        double motorErrorTicks = motorTargetTicks - motorCurrentTicks;

        if (Math.abs(motorTicksError) < 4) {
            turretMotor.setPower(0);
            pidf.reset();
            return;
        }

        pidf.updateError(motorTicksError);
        pidf.updateFeedForwardInput(0);

        double turretMotorPower = pidf.run();
        turretMotor.setPower(Math.max(-1, Math.min(1, turretMotorPower)));
    }

    // RADIANS TO TICKS
    private double radToTicks(double rad) {
        return (rad / (2 * Math.PI)) * TICKS_PER_REV * GEAR_RATIO;
    }

    // TICKS TO RADIANS
    public double ticksToRad(double ticks) {
        return (ticks / (TICKS_PER_REV * GEAR_RATIO)) * (2 * Math.PI);
    }

    // CLAMP ANGLE
    private double clamp(double angle) {
        return Math.max(-MAX_ANGLE, Math.min(MAX_ANGLE, angle));
    }

    // WEAP ANGLE
    private double wrap(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

}
