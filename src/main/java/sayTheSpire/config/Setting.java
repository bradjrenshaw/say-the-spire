package sayTheSpire.config;

import com.google.gson.JsonElement;
import sayTheSpire.Output;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.screens.Screen;;

public abstract class Setting {

    protected LocalizationContext localization;
    private SettingCategory parent;
    private String name;

    public Setting(SettingCategory parent, String name) {
        this.parent = parent;
        this.name = name;
        this.localization = Output.localization.getContext(this.getLocalizationPath());
    }

    public abstract DynamicElement getDynamicElement(Screen screen);

    public abstract void fromJsonElement(JsonElement json);

    public abstract JsonElement toJsonElement();

    public abstract Object getValue();

    public abstract void setValue(Object value);

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
        return parent.getLocalizationPath() + ".settings." + this.getName();
    }

    public String getName() {
        return this.name;
    }

    public SettingCategory getParent() {
        return this.parent;
    }

}
