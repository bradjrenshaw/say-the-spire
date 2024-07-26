package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sayTheSpire.Output;

public class InputManager {

    private static final Logger logger = LogManager.getLogger(InputManager.class.getName());
    private InputActionCollection baseActionCollection;
    private ArrayList<ControllerInputMapping> controllerMappings;

    public InputManager() {
        this.controllerMappings = new ArrayList();
        this.baseActionCollection = this.buildBaseActionCollection();
    }

    public void RegisterControllerMappingIfValid(InputMapping mapping) {
        if (!(mapping instanceof ControllerInputMapping)) {
            return;
        }
        this.controllerMappings.add((ControllerInputMapping) mapping);
    }

    public void unregisterControllerMappingIfValid(InputMapping mapping) {
        if (this.controllerMappings.contains(mapping)) {
            this.controllerMappings.remove(mapping);
        }
    }

    public void clearActionStates() {
        for (InputAction action : this.baseActionCollection.getActions()) {
            action.clearStates();
        }
    }

    private InputActionCollection buildBaseActionCollection() {
        return InputBuilder.buildBaseActions(this);
    }

    public void handleControllerKeycodePress(int keycode) {
        for (ControllerInputMapping mapping : this.controllerMappings) {
            mapping.handleKeycodePress(keycode);
        }
    }

    public void handleControllerKeycodeRelease(int keycode) {
        for (ControllerInputMapping mapping : this.controllerMappings) {
            mapping.handleKeycodeRelease(keycode);
        }
    }

    public void updateFirst() {
        if (!Output.config.getBoolean("input.virtual_input"))
            return;
        for (InputAction action : this.baseActionCollection.getActions()) {
            action.updateFirst();
        }
    }

    public void updateLast() {
        if (!Output.config.getBoolean("input.virtual_input"))
            return;
        for (InputAction action : this.baseActionCollection.getActions()) {
            action.updateLast();
        }
    }

    public void fromJson(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        JsonObject baseObj = obj.get("base").getAsJsonObject();
        this.baseActionCollection.fromJson(baseObj);
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("base", this.baseActionCollection.toJsonElement());
        return obj;
    }
}
