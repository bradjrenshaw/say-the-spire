package sayTheSpire.ui.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import sayTheSpire.Output;
import sayTheSpire.ui.mod.Context;

public class InputManager {
    private static final Logger logger = LogManager.getLogger(InputManager.class.getName());
    private HashMap<String, InputAction> actions;
    private HashMap<Integer, Boolean> controllerPressed, keyboardPressed;
    private HashSet<Integer> keysToCheck;
    private HashSet<InputMapping.Modifiers> keyboardPressedModifiers;
    private InputActionCollection actionCollection;
    static final String actionNames[] = { "select", "cancel", "top panel", "proceed", "peek", "page left", "page right",
            "draw pile", "discard pile", "map", "settings", "up", "down", "left", "right", "alt up", "alt down",
            "alt left", "alt right", "inspect up", "inspect down", "inspect left", "inspect right", "read player block",
            "read player gold", "read player hp", "read act boss" };

    public InputManager(JSONObject inputObj) {
        if (inputObj != null) {
            this.actionCollection = new InputActionCollection(this, inputObj);
        } else {
            this.actionCollection = new InputActionCollection(this);
        }
        this.keysToCheck = new HashSet();
        this.controllerPressed = new HashMap();
        this.keyboardPressed = new HashMap();
        this.keyboardPressedModifiers = new HashSet();
        this.determineKeysToCheck();
    }

    public void clearActionStates() {
        if (this.controllerPressed != null)
            this.controllerPressed.clear();
        if (this.keyboardPressed != null)
            this.keyboardPressed.clear();
        for (InputAction action : this.actionCollection.getActions()) {
            action.clearStates();
        }
    }

    public InputActionCollection getActionCollection() {
        return this.actionCollection;
    }

    private void determineKeysToCheck() {
        this.keysToCheck.clear();
        for (InputAction action : this.actionCollection.getActions()) {
            for (InputMapping mapping : action.getMappings()) {
                int keycode = mapping.getKeycode();
                this.keysToCheck.add(keycode);
            }
        }
    }

    public void handleControllerKeycodePress(int keycode) {
        this.controllerPressed.put(keycode, true);
    }

    public void handleControllerKeycodeRelease(int keycode) {
        this.controllerPressed.remove(keycode);
    }

    private Boolean isMappingJustPressedController(InputMapping mapping) {
        int keycode = mapping.getKeycode();
        return this.controllerPressed.getOrDefault(keycode, false);
    }

    private Boolean isMappingPressedController(InputMapping mapping) {
        int keycode = mapping.getKeycode();
        return this.controllerPressed.containsKey(keycode);
    }

    private Boolean isMappingJustPressedKeyboard(InputMapping mapping) {
        return mapping.getModifiers().equals(this.keyboardPressedModifiers)
                && this.keyboardPressed.getOrDefault(mapping.getKeycode(), false);
    }

    private Boolean isMappingPressedKeyboard(InputMapping mapping) {
        return mapping.getModifiers().equals(this.keyboardPressedModifiers)
                && this.keyboardPressed.containsKey(mapping.getKeycode());
    }

    public Boolean isMappingJustPressed(InputMapping mapping) {
        switch (mapping.getInputType()) {
        case "controller":
            return this.isMappingJustPressedController(mapping);
        case "keyboard":
            return this.isMappingJustPressedKeyboard(mapping);
        default:
            return false;
        }
    }

    public Boolean isMappingPressed(InputMapping mapping) {
        switch (mapping.getInputType()) {
        case "controller":
            return this.isMappingPressedController(mapping);
        case "keyboard":
            return this.isMappingPressedKeyboard(mapping);
        default:
            return false;
        }
    }

    private void updateKeyboardState() {
        this.keyboardPressedModifiers.clear();
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
            this.keyboardPressedModifiers.add(InputMapping.Modifiers.CONTROL);
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
            this.keyboardPressedModifiers.add(InputMapping.Modifiers.SHIFT);
        if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Keys.ALT_RIGHT))
            this.keyboardPressedModifiers.add(InputMapping.Modifiers.ALT);
        for (int keycode : this.keysToCheck) {
            if (Gdx.input.isKeyPressed(keycode))
                this.keyboardPressed.put(keycode, Gdx.input.isKeyJustPressed(keycode));
            else
                this.keyboardPressed.remove(keycode);
        }
    }

    public void updateFirst() {
        this.updateKeyboardState();
        for (InputAction action : this.actionCollection.getActions()) {
            action.update();
        }
    }

    public void updateLast() {
        if (!Output.config.getBoolean("input.virtual_input"))
            return;
        for (Map.Entry<Integer, Boolean> entry : this.controllerPressed.entrySet()) {
            if (entry.getValue()) {
                entry.setValue(false); // change just pressed to pressed
            }
        }
    }
}
