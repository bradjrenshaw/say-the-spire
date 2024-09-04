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
    private InputActionCollection baseActionCollection, activeActionCollection;
    private ArrayList<ControllerInputMapping> controllerMappings;

    public InputManager() {
        this.controllerMappings = new ArrayList();
        this.baseActionCollection = this.buildBaseActionCollection();
        this.modifyActions();
    }

    public void modifyActions() {
        this.activeActionCollection = this.buildActiveActionCollection();
        this.controllerMappings.clear();
        for (InputAction action : this.activeActionCollection.getActions()) {
            for (ControllerInputMapping mapping : action.getControllerMappings()) {
                this.addControllerMapping(mapping);
            }
        }
    }

    private InputActionCollection buildActiveActionCollection() {
        InputActionCollection collection = this.baseActionCollection.copy();
        return collection;
    }

    private void addControllerMapping(ControllerInputMapping mapping) {
        this.controllerMappings.add(mapping);
    }

    public void clearActionStates() {
        for (InputAction action : this.baseActionCollection.getActions()) {
            action.clearStates();
        }
    }

    private InputActionCollection buildBaseActionCollection() {
        return InputBuilder.buildBaseActionCollection();
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
        for (InputAction action : this.activeActionCollection.getActions()) {
            action.updateFirst();
        }
    }

    public void updateLast() {
        if (!Output.config.getBoolean("input.virtual_input"))
            return;
        for (InputAction action : this.activeActionCollection.getActions()) {
            action.updateLast();
        }
    }

    public void fromJson(JsonElement json) {
        // If plugins are implemented, needs refactor to separate base actions from plugin actions
        JsonObject obj = json.getAsJsonObject();
        JsonObject baseObj = obj.get("base").getAsJsonObject();
        this.activeActionCollection.fromJson(baseObj);
        this.modifyActions();
    }

    public JsonElement toJsonElement() {
        // If plugins are implemented, needs refactor to separate base actions from plugin actions
        JsonObject obj = new JsonObject();
        obj.add("base", this.activeActionCollection.toJsonElement());
        return obj;
    }
}
