package sayTheSpire.ui.dynamic.elements.settings;

import java.util.ArrayList;

import sayTheSpire.config.ChoiceArraySetting;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.screens.ChoiceArraySettingScreen;

public class DynamicSettingsChoiceArrayButton extends DynamicButton {

    private UIContext context;
    private ChoiceArraySetting setting;

    public DynamicSettingsChoiceArrayButton(UIContext context, ChoiceArraySetting setting) {
        super(setting.getLabel());
        this.context = context;
        this.setting = setting;
    }

    public void onClick() {
        super.onClick();
        this.context.pushScreen(new ChoiceArraySettingScreen(this.context, this.setting));
    }

    public String getDescription() {
        return this.setting.getDescription();
    }

    public String getStatusString() {
        return String.join(", ", (ArrayList<String>) this.setting.getValue());
    }

}
