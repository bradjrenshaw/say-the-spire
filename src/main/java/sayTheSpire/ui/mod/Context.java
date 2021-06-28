package sayTheSpire.ui.mod;

import sayTheSpire.ui.input.InputAction;


public class Context {

    protected Boolean shouldForceControllerMode = false;
    protected Boolean allowVirtualInput = true;

    public Boolean getAllowVirtualInput() {
        return this.allowVirtualInput;
    }

    public Boolean getShouldForceControllerMode() {
        return this.shouldForceControllerMode;
    }

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
