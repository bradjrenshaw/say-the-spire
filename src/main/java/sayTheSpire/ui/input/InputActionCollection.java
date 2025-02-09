package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import com.badlogic.gdx.Input.Keys;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;

/** Manages stored input mappings as well as assigning those and default mappings to actions */
public class InputActionCollection {

    private static final Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");
    private LinkedHashMap<String, InputAction> actions;

    public InputActionCollection() {
        this.actions = new LinkedHashMap();
    }

    public InputAction addAction(String key) {
        InputAction action = new InputAction(key);
        this.actions.put(action.getKey(), action);
        return action;
    }

    public InputAction addAction(InputAction action) {
        this.actions.put(action.getKey(), action);
        return action;
    }

    public Collection<InputAction> getActions() {
        return this.actions.values();
    }

    public InputActionCollection copy() {
        InputActionCollection newCollection = new InputActionCollection();
        for (InputAction action : this.actions.values()) {
            newCollection.addAction(action.copy());
        }
        return newCollection;
    }

    public void fromJson(JsonObject obj) {
        for (Map.Entry<String, InputAction> entry : this.actions.entrySet()) {
            JsonElement actionElement = obj.get(entry.getKey());
            if (actionElement != null) {
                entry.getValue().fromJson(actionElement.getAsJsonArray());
            }
        }
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        for (InputAction action : this.actions.values()) {
            obj.add(action.getKey(), action.toJsonElement());
        }
        return obj;
    }
}
