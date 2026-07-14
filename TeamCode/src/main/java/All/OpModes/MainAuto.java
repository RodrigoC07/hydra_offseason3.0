package All.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;

import All.Configs.Auto.AutoLogic;
import All.Configs.PP.Constants;

@Autonomous
public class MainAuto extends OpMode {

    // CONSTANTS
    private Timer pathTimer, opModeTimer;
    private ElapsedTime time = new ElapsedTime();
    private AutoLogic autoLogic = new AutoLogic();

    public enum AutoState {

        LIFT1,
        DOWN1,
        END

    }

    AutoState autoState;

    public void setAutoState(AutoState newState) {
        autoState = newState;
        pathTimer.resetTimer();
    }

    public void autoStateUpdate () {

        switch (autoState) {
            case LIFT1:
                autoLogic.UP_OPEN();
                setAutoState(AutoState.DOWN1);
                break;
            case DOWN1:
                autoLogic.DOWN_CLOSE();
                setAutoState(AutoState.END);
                break;
            case END:
                autoLogic.STOP();
                break;

        }


    }

    @Override
    public void init() {

        pathTimer = new Timer();
        opModeTimer = new Timer();
        time.reset();

        autoLogic.init(hardwareMap);

        autoState = AutoState.LIFT1;

    }

    @Override
    public void loop() {

        CommandScheduler.getInstance().run();
        autoLogic.update();
        autoStateUpdate();

    }

    @Override
    public void stop() {
        autoLogic.stopAll();
    }
}
