package sayTheSpire.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import sayTheSpire.ui.IUIInfo;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.elements.settings.DynamicSettingsKeyArrayButton;
import sayTheSpire.ui.dynamic.screens.Screen;

public class KeyArraySetting extends Setting {

    private LinkedHashMap<String, IUIInfo> items;
    private HashMap<String, IUIInfo> choices;

    public KeyArraySetting(SettingCategory parent, String name) {
        this(parent, name, null);
    }

    public KeyArraySetting(SettingCategory parent, String name, ArrayList<String> defaultKeys) {
        super(parent, name);
        this.items = new LinkedHashMap<>();
        this.choices = new HashMap<>();
    }

    public String getChoicePrompt() {
        return this.localization.localize("choicePrompt");
    }

    public DynamicElement getDynamicElement(Screen screen) {
        return new DynamicSettingsKeyArrayButton(screen.getContext(), this);
    }

    public LinkedHashMap<String, IUIInfo> getItems() {
        return this.items;
    }

    public HashMap<String, IUIInfo> getChoices() {
        return this.choices;
    }

    public Boolean add(String key) {
        if (this.items.containsKey(key)) {
            return false;
        }
        IUIInfo item = this.choices.getOrDefault(key, null);
        if (item == null) {
            throw new RuntimeException("Attempt to add missing key " + key + " to KeyArray setting.");
        }
        this.items.put(key, item);
        return true;
    }

    public Boolean addChoice(IUIInfo choice) {
        // putIfAbsent returns null if there was no matching key already there
        return this.choices.putIfAbsent(choice.getKey(), choice) == null;
    }

    public Boolean remove(String key) {
        if (!this.items.containsKey(key)) {
            return false;
        }
        this.items.remove(key);
        return true;
    }

    public void fromJsonElement(JsonElement json) {
        this.items.clear();
        for (JsonElement e : json.getAsJsonArray()) {
            this.add(e.getAsString());
        }
    }

    public JsonElement toJsonElement() {
        JsonArray array = new JsonArray();
        for (String item : this.items.keySet()) {
            array.add(item);
        }
        return array;
    }

    public Object getValue() {
        return new ArrayList(this.items.keySet());
    }

    protected void setValue(Object value, Boolean ignoreLock) {
        if (this.getLocked() && !ignoreLock)
            return;
        ArrayList<String> values = (ArrayList<String>) value;
        for (String key : values) {
            this.add(key);
        }
    }

}
