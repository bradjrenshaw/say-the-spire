package sayTheSpire.ui.dynamic.elements.settings;

import sayTheSpire.config.BooleanSetting;
import sayTheSpire.config.Setting;
import sayTheSpire.ui.dynamic.elements.DynamicToggleButton;

public class DynamicSettingsToggleButtonElement extends DynamicToggleButton {

    private BooleanSetting setting;

    public DynamicSettingsToggleButtonElement(BooleanSetting setting) {
        super(setting.getLabel());
        this.setting = setting;
    }

    public Boolean getEnabled() {
        if (this.setting == null)
            return false;
        return (Boolean) this.setting.getValue();
    }

    public void setEnabled(Boolean value) {
        if (this.setting == null)
            return;
        this.setting.setValue(value);
    }

    public String getDescription() {
        return this.setting.getDescription();
    }

    public String getLabel() {
        return this.setting.getLabel();
    }

}
