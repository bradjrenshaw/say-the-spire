package sayTheSpire.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.elements.DynamicToggleButton;
import sayTheSpire.ui.dynamic.elements.settings.DynamicSettingsToggleButtonElement;
import sayTheSpire.ui.dynamic.screens.Screen;
import sayTheSpire.ui.dynamic.events.EventHandler;
import sayTheSpire.ui.dynamic.events.ToggleChangeEvent;

public class BooleanSetting extends Setting {

    private Boolean defaultValue;
    private Boolean value;

    public BooleanSetting(SettingCategory parent, String name, Boolean defaultValue) {
        super(parent, name);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public DynamicElement getDynamicElement(Screen screen) {
        return new DynamicSettingsToggleButtonElement(this);
    }

    public void fromJsonElement(JsonElement json) {
        this.setValue(json.getAsBoolean());
    }

    public JsonElement toJsonElement() {
        return new JsonPrimitive((Boolean) this.getValue());
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = (Boolean) value;
    }

}
