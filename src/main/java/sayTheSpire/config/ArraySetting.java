package sayTheSpire.config;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.screens.Screen;

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

    public DynamicElement getDynamicElement(Screen screen) {
        return new DynamicButton("Array will go here");
    }

    public void addValue(String value) {
        this.items.add(value);
    }

    public Boolean removeValue(String value) {
        if (this.items.indexOf(value) >= 0) {
            this.items.remove(value);
            return true;
        }
        return false;
    }

    public void fromJsonElement(JsonElement json) {
        System.out.println("Parsing setting from config " + this.getLabel());
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

    protected void setValue(Object value, Boolean ignoreLock) {
        if (this.getLocked() && !ignoreLock)
            return;
        ArrayList<String> values = (ArrayList<String>) value;
        this.items = values;
    }

}
