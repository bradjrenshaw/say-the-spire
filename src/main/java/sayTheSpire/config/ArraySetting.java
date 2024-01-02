package sayTheSpire.config;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ArraySetting extends Setting {

    private ArrayList<String> defaultValues;
    private ArrayList<String> items;

    public ArraySetting(SettingCategory parent, String name) {
        this(parent, name, null);
    }

    public ArraySetting(SettingCategory parent, String name, ArrayList<String> defaultValues) {
        super(parent, name);
        if (defaultValues != null) {
            this.defaultValues = defaultValues;
        } else {
            this.defaultValues = new ArrayList();
        }
        this.items = this.defaultValues;
    }

    public void addValue(String value) {
        this.items.add(value);
    }

    public void fromJsonElement(JsonElement json) {
        this.items.clear();
        for (JsonElement e : json.getAsJsonArray()) {
            this.addValue(e.getAsString());
        }
    }

    public JsonElement toJsonElement() {
        JsonArray array = new JsonArray();
        for (String item : this.items) {
            array.add(item);
        }
        return array;
    }

    public Object getValue() {
        return this.items;
    }

    public void setValue(Object value) {
        ArrayList<String> values = (ArrayList<String>) value;
        this.items = values;
    }

}
