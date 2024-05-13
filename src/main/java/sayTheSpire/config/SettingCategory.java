package sayTheSpire.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import sayTheSpire.localization.LocalizedStringIdentifier;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.elements.settings.DynamicSettingsCategoryButton;
import sayTheSpire.ui.dynamic.screens.Screen;

public class SettingCategory extends Setting {

    private LinkedHashMap<String, Setting> settings;

    public SettingCategory(SettingCategory parent, String name) {
        super(parent, name);
        this.settings = new LinkedHashMap();
    }

    public DynamicElement getDynamicElement(Screen screen) {
        return new DynamicSettingsCategoryButton(screen.getContext(), this);
    }

    public void add(Setting setting) {
        this.settings.put(setting.getKey(), setting);
    }

    public KeyArraySetting addArray(String name) {
        return this.addArray(name, null);
    }

    public KeyArraySetting addArray(String name, ArrayList<String> defaultValues) {
        KeyArraySetting setting = new KeyArraySetting(this, name, defaultValues);
        this.add(setting);
        return setting;
    }

    public BooleanSetting addBoolean(String name, Boolean defaultValue) {
        BooleanSetting setting = new BooleanSetting(this, name, defaultValue);
        this.add(setting);
        return setting;
    }

    public SettingCategory addCategory(String name) {
        SettingCategory category = new SettingCategory(this, name);
        this.add(category);
        return category;
    }

    public KeyArraySetting addKeyArray(String name) {
        KeyArraySetting setting = new KeyArraySetting(this, name);
        this.add(setting);
        return setting;
    }

    public Setting get(String name) {
        return this.settings.get(name);
    }

    public Setting getSettingFromPath(String path) {
        SettingCategory current = this;
        String pathArray[] = path.split("\\.");
        for (int i = 0; i < pathArray.length; i++) {
            Setting result = current.get(pathArray[i]);
            if (i == pathArray.length - 1) {
                return result;
            } else if (result instanceof SettingCategory) {
                current = (SettingCategory) result;
            } else {
                throw new RuntimeException("Reached bottom of setting hierarchy before end of path string.");
            }
        }
        return null;
    }

    public void fromJsonElement(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Setting setting = this.settings.getOrDefault(entry.getKey(), null);
            if (setting != null) {
                setting.fromJsonElement(entry.getValue());
            }
        }
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        for (Setting setting : this.settings.values()) {
            obj.add(setting.getKey(), setting.toJsonElement());
        }
        return obj;
    }

    public Object getValue() {
        return null;
    }

    public void setValue(Object value, Boolean locked) {
        throw new RuntimeException("Setting category has no value to set.");
    }

    public LinkedHashMap<String, Setting> getSettings() {
        return this.settings;
    }
}
