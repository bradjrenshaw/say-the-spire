package sayTheSpire.ui.dynamic.contexts;

import sayTheSpire.config.SettingCategory;
import sayTheSpire.ui.dynamic.screens.SettingsCategoryScreen;

public class SettingsContext extends UIContext {

    private SettingCategory root;

    public SettingsContext(SettingCategory root) {
        super();
        this.root = root;
    }

    public void onPush() {
        SettingsCategoryScreen screen = new SettingsCategoryScreen(this, this.root);
        this.pushScreen(screen);
    }

}
