package sayTheSpire.ui.dynamic.elements.settings;

import java.util.ArrayList;
import sayTheSpire.config.KeyArraySetting;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.screens.KeyArraySettingScreen;

public class DynamicSettingsKeyArrayButton extends DynamicButton {

    private UIContext context;
    private KeyArraySetting setting;

    public DynamicSettingsKeyArrayButton(UIContext context, KeyArraySetting setting) {
        super(setting.getLabel());
        this.context = context;
        this.setting = setting;
    }

    public void onClick() {
        super.onClick();
        this.context.pushScreen(new KeyArraySettingScreen(this.context, this.setting));
    }

    public String getDescription() {
        return this.setting.getDescription();
    }

    public String getStatusString() {
        return String.join(", ", (ArrayList<String>) this.setting.getValue());
    }

}
