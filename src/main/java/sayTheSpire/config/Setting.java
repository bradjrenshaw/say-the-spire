package sayTheSpire.config;

import com.google.gson.JsonElement;
import sayTheSpire.Output;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.screens.Screen;;

public abstract class Setting {

    protected LocalizationContext localization;
    private SettingCategory parent;
    private String key;
    private Boolean locked;

    public Setting(SettingCategory parent, String key) {
        this.parent = parent;
        this.key = key;
        this.locked = false;
        this.localization = Output.localization.getContext(this.getLocalizationPath());
    }

    public abstract DynamicElement getDynamicElement(Screen screen);

    public abstract void fromJsonElement(JsonElement json);

    public abstract JsonElement toJsonElement();

    public abstract Object getValue();

    public void setValue(Object value) {
        setValue(value, false);
    }

    protected abstract void setValue(Object value, Boolean ignoreLock);

    public String getDescription() {
        return this.localization.localize("description");
    }

    public String getLabel() {
        return this.localization.localize("label");
    }

    public String getLocalizationPath() {
        SettingCategory parent = this.getParent();
        if (parent == null) {
            return "config.settings.base";
        }
        return parent.getLocalizationPath() + ".settings." + this.getKey();
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean value) {
        this.locked = value;
    }

    public String getKey() {
        return this.key;
    }

    public SettingCategory getParent() {
        return this.parent;
    }

}
