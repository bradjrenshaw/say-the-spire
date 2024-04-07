package sayTheSpire.ui.dynamic.screens;

import sayTheSpire.Output;
import sayTheSpire.config.SettingCategory;
import sayTheSpire.ui.dynamic.contexts.SettingsContext;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.DynamicToggleButton;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.EventHandler;
import sayTheSpire.ui.dynamic.events.ToggleChangeEvent;
import sayTheSpire.ui.input.InputAction;

public class ModMenuScreen extends Screen {

    public ModMenuScreen(UIContext context) {
        super(context);
    }

    protected void setup() {
        ListContainer children = new ListContainer("Mod Menu", true);
        children.add(new DynamicButton(Output.modVersion, null));
        DynamicButton settings = new DynamicButton("settings");
        settings.click.registerHandler(new EventHandler<ClickEvent>() {
            public Boolean execute(ClickEvent event) {
                SettingCategory category = Output.config.getSettings().getRoot();
                Output.uiManager.pushContext(new SettingsContext(category));
                return true;
            }
        });
        children.add(settings);
        this.setPrimaryContainer(children);
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (super.processInputJustPressed(action))
            return true;
        if (action.getKey() == "mod menu")
            return true;
        if (action.getKey() == "cancel") {
            Output.uiManager.popContext();
            Output.text("Mod menu closed.", false);
            return true;
        }
        return false;
    }
}
