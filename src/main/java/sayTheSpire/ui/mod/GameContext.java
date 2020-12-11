package sayTheSpire.ui.mod;

import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import sayTheSpire.Output;

public class GameContext extends Context {

    public void onClearJust() {
        //clear here
    }

    public Boolean onJustPress(InputAction action) {
        Output.silenceSpeech();
        switch(action.getName()) {
            case "inspect up":
            Output.infoControls(Output.Direction.UP);
            break;
            case "inspect down":
            Output.infoControls(Output.Direction.DOWN);
            break;
            case "inspect left":
            Output.infoControls(Output.Direction.LEFT);
            break;
            case "inspect right":
            Output.infoControls(Output.Direction.RIGHT);
            break;
        }
        CInputAction gameAction = action.getGameControllerAction();
                if (gameAction != null) {
                    action.setGameControllerActionPressed(true);
                    action.setGameControllerActionJustPressed(true);
        }
        return true;
    }

    public Boolean onJustRelease(InputAction action) {
        CInputAction gameAction = action.getGameControllerAction();
        if (gameAction != null) {
            action.setGameControllerActionJustReleased(true);
            action.setGameControllerActionPressed(false);
        }
        return true;
    }
}