package All.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import All.Configs.PP.Constants;

public class Drive extends SubsystemBase {

    // MOTORS
    private final DcMotorEx LMF, LMB, RMF, RMB;

    // LOCALIZATION
    private final GoBildaPinpointDriver pinpoint;
    private final Follower follower;

    // DRIVE CONSTANTS
    private double driveSpeed = 0.95;
    private double currentHeadingRad = 0.0;
    private double headingOffSet = 0.0;
    private static final double INIT_FIELD_OFFSET = Math.PI;
    private boolean headingInit = false;


    public Drive(HardwareMap hwMap) {
        LMF = hwMap.get(DcMotorEx.class, "LMF");
        LMB = hwMap.get(DcMotorEx.class, "LMB");
        RMF = hwMap.get(DcMotorEx.class, "RMF");
        RMB = hwMap.get(DcMotorEx.class, "RMB");

        LMF.setDirection(DcMotorEx.Direction.REVERSE);
        LMB.setDirection(DcMotorEx.Direction.REVERSE);

        pinpoint = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");

        follower = Constants.createFollower(hwMap);

    }

    // PERIODIC
    @Override
    public void periodic() {

        currentHeadingRad = pinpoint.getHeading(AngleUnit.RADIANS);

        if (!headingInit) {
            headingOffSet = currentHeadingRad - INIT_FIELD_OFFSET;
            headingInit = true;
        }

    }

    // COMMANDS
    public void updatePinpoint() {follower.update();}

    public void resetFieldOrietation() {headingOffSet = currentHeadingRad;}

    // CALCS
    public void drive(double x, double y, double turn) {

        double robotHeading = currentHeadingRad - headingOffSet;

        double fieldX = x * Math.cos(-robotHeading) - y * Math.sin(-robotHeading);
        double fieldY = x * Math.sin(-robotHeading) - y * Math.cos(-robotHeading);

        double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(turn), 1);

        double LMFpower = (fieldY + fieldX + turn) /denominator * driveSpeed;
        double LMBpower = (fieldY - fieldX + turn) /denominator * driveSpeed;
        double RMFpower = (fieldY - fieldX - turn) /denominator * driveSpeed;
        double RMBpower = (fieldY + fieldX - turn) /denominator * driveSpeed;

        LMF.setPower(LMFpower);
        LMB.setPower(LMBpower);
        RMF.setPower(RMFpower);
        RMB.setPower(RMBpower);

    }

    // SET AND GET
    public void setStartPose (Pose startPose) {follower.setStartingPose(startPose);}

    public Pose getRobotPose () {return follower.getPose();}
    public double getRobotHeadingRad () {return currentHeadingRad;}

}
