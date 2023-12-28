package sayTheSpire.ui.dynamic.contexts;

import sayTheSpire.ui.dynamic.screens.ModMenuScreen;

public class ModMenuContext extends UIContext {

    private Boolean setup;

    public ModMenuContext() {
        super();
        this.setup = false;
    }

    public void onPush() {
        this.pushScreen(new ModMenuScreen(this));
    }

}
