package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;

/** Manages stored input mappings as well as assigning those and default mappings to actions */
public class InputActionCollection {

    private static final Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");
    private InputManager inputManager;
    private HashMap<String, InputAction> actions;
    private HashMap<String, ArrayList<InputMapping>> defaults;

    public InputActionCollection(InputManager manager) {
        this.inputManager = manager;
        this.actions = new HashMap();
    }

    public InputAction addAction(String key) {
        InputAction action = new InputAction(key, this.inputManager);
        this.actions.put(action.getKey(), action);
        return action;
    }

    public Collection<InputAction> getActions() {
        return this.actions.values();
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
