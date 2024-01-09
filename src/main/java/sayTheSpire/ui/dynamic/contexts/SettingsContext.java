package sayTheSpire.ui.dynamic.contexts;

import sayTheSpire.ui.dynamic.screens.SettingsCategoryScreen;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.Output;
import sayTheSpire.config.SettingCategory;
import sayTheSpire.ui.dynamic.screens.SettingsCategoryScreen;

public class SettingsContext extends UIContext {

    private SettingCategory root;

    public SettingsContext(SettingCategory root) {
        super();
        this.root = root;
    }

    private void handleCancel() {
        if (!this.screens.empty()) {
            this.popScreen();
            if (this.screens.empty()) {
                Output.uiManager.popContext();
            }
        }
    }

    public void onPush() {
        SettingsCategoryScreen screen = new SettingsCategoryScreen(this, this.root);
        this.pushScreen(screen);
    }

    public Boolean onJustPress(InputAction action) {
        Boolean result = super.onJustPress(action);
        if (action.getName() == "cancel") {
            this.handleCancel();
            return true;
        }
        return result;
    }
}
