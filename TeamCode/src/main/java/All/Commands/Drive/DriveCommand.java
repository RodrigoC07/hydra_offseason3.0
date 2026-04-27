package All.Commands.Drive;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import All.Subsystems.Drive;

public class DriveCommand extends CommandBase {

    // SUBSYSTEM
    private final Drive drive;

    // GAMEPADS
    private final GamepadEx gamepad;

    // CONSTANTS
    private double DEAD_ZONE = 0.05;

    public DriveCommand (Drive drive, GamepadEx gamepad) {
        this.drive = drive;
        this.gamepad = gamepad;

        addRequirements(drive);
    }

    @Override
    public void execute () {

        double x = apllyDeadZone(gamepad.getLeftX());
        double y = apllyDeadZone(-gamepad.getLeftY());
        double turn = apllyDeadZone(gamepad.getRightX());

        drive.drive(x, y, turn);

    }

    private double apllyDeadZone (double v) {return Math.abs(v) > DEAD_ZONE ? v : 0;}

}

