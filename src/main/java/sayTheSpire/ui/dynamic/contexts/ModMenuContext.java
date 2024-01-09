package sayTheSpire.ui.dynamic.contexts;

import sayTheSpire.Output;
import sayTheSpire.ui.dynamic.screens.ModMenuScreen;

public class ModMenuContext extends UIContext {

    public ModMenuContext() {
        super();
    }

    public void onPush() {
        this.pushScreen(new ModMenuScreen(this));
    }

}
