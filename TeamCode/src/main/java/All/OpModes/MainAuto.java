package All.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;

import All.Configs.Auto.AutoLogic;

@Autonomous
public class MainAuto extends OpMode {

    // CONSTANTS
    private AutoLogic autoLogic = new AutoLogic();

    public enum AutoState {
        UP_AND_OPEN1,
        DOWN_AND_CLOSE1
    }

    AutoState autoState;

    public void autoStateUpdate () {

        switch (autoState) {

            case UP_AND_OPEN1:
                autoLogic.up_open();
                setAutoState(AutoState.DOWN_AND_CLOSE1);
            case DOWN_AND_CLOSE1:
                autoLogic.down_close();
                break;

        }

    }

    public void setAutoState (AutoState newState) {
        autoState = newState;
    }


    @Override
    public void init() {

        autoLogic.init(hardwareMap);
        autoState = AutoState.UP_AND_OPEN1;

    }

    @Override
    public void loop() {

        CommandScheduler.getInstance().run();
        autoLogic.updateAutoLogic();
        autoStateUpdate();
        
    }

    @Override
    public void stop() {

    }
}
