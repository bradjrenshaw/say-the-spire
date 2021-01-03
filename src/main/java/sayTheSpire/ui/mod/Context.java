package sayTheSpire.ui.mod;

import sayTheSpire.ui.mod.InputAction;


public class Context {


    public void onFocus() {
        return;
    }

    public Boolean onJustPress(InputAction action) {
        return false;
    }

    public Boolean onPress(InputAction action) {
        return false;
    }

    public Boolean onJustRelease(InputAction action) {
        return false;
    }

    public void onUnfocus() {
        return;
    }
}
