package sayTheSpire.ui.dynamic.screens;

import sayTheSpire.Output;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.DynamicToggleButton;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.dynamic.events.EventHandler;
import sayTheSpire.ui.dynamic.events.ToggleChangeEvent;
import sayTheSpire.ui.input.InputAction;

public class ModMenuScreen extends Screen {

    public ModMenuScreen(UIContext context) {
        super(context);
    }

    protected void setup() {
        ListContainer children = new ListContainer(null, "Mod Menu", true);
        children.add(new DynamicButton(children, Output.modVersion, null));
        children.add(new DynamicButton(children, "Settings", "Settings not yet implemented."));
        this.setPrimaryContainer(children);
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (super.processInputJustPressed(action))
            return true;
        if (action.getName() == "mod menu")
            return true;
        if (action.getName() == "cancel") {
            Output.uiManager.popContext();
            Output.text("Mod menu closed.", false);
            return true;
        }
        return false;
    }
}
