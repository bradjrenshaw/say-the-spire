package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.Settings;
import sayTheSpire.Output;
import sayTheSpire.ui.mod.UIManager;
import sayTheSpire.ui.mod.Context;

public class InputManager {
    private static final Logger logger = LogManager.getLogger(InputManager.class.getName());
    private HashMap<String, InputAction> actions;
    private HashMap<Integer, Boolean> controllerPressed, keyboardPressed;
    private HashSet<Integer> keysToCheck;
    private EnumSet keyboardPressedModifiers;
    private InputConfig inputConfig;
    UIManager uiManager;

    public InputManager(UIManager uiManager, InputConfig inputConfig) {
        this.uiManager = uiManager;
        this.inputConfig = inputConfig;
    this.actions = new HashMap();
    this.setupActions();
    this.keysToCheck = new HashSet();
    this.controllerPressed = new HashMap();
    this.keyboardPressed = new HashMap();
    this.keyboardPressedModifiers = EnumSet.noneOf(InputMapping.Modifiers.class);
    this.inputConfig.populateActions(this.actions);
    this.determineKeysToCheck();
    }

    public void addAction(InputAction action) {
        String name = action.getName();
        if (this.actions.containsKey(name)) {
            throw new RuntimeException("Error: InputManager: An action with name " + name + " already exists.");
        }
        this.actions.put(name, action);
    }

    public void clearActionStates() {
        if (this.controllerPressed != null) this.controllerPressed.clear();
        if (this.keyboardPressed != null) this.keyboardPressed.clear();
        for (InputAction action:this.actions.values()) {
            action.clearStates();
        }
    }

    private void determineKeysToCheck() {
        this.keysToCheck.clear();
        for (InputAction action:this.actions.values()) {
            for (InputMapping mapping:action.getMappings()) {
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
        return mapping.getModifiers().equals(this.keyboardPressedModifiers) && this.keyboardPressed.getOrDefault(mapping.getKeycode(), false);
    }

    private Boolean isMappingPressedKeyboard(InputMapping mapping) {
        return mapping.getModifiers().equals(this.keyboardPressedModifiers) && this.keyboardPressed.containsKey(mapping.getKeycode());
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

    private void setupActions() {
        String actionNames[] = {"select", "cancel", "top panel", "proceed", "peek", "page left", "page right", "draw pile", "discard pile", "map", "settings", "up", "down", "left", "right", "alt up", "alt down", "alt left", "alt right", "inspect up", "inspect down", "inspect left", "inspect right"};
        for (String name:actionNames) {
            this.addAction(new InputAction(name, this));
        }
    }

    private void updateKeyboardState() {
        this.keyboardPressedModifiers.clear();
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) this.keyboardPressedModifiers.add(InputMapping.Modifiers.CONTROL);
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) this.keyboardPressedModifiers.add(InputMapping.Modifiers.SHIFT);
        if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) this.keyboardPressedModifiers.add(InputMapping.Modifiers.ALT);
        for (int keycode:this.keysToCheck) {
            if (Gdx.input.isKeyPressed(keycode)) this.keyboardPressed.put(keycode, Gdx.input.isKeyJustPressed(keycode));
            else this.keyboardPressed.remove(keycode);
        }
    }

    public void updateFirst() {
        if (!Output.config.getBoolean("input.virtual_input")) return;
Context current  = this.uiManager.getCurrentContext();
    if (current.getShouldForceControllerMode()) {
        Settings.isControllerMode = true;
    }
        this.updateKeyboardState();
        for (InputAction action:this.actions.values()) {
            action.update();
        }
    }

    public void updateLast() {
                if (!Output.config.getBoolean("input.virtual_input")) return;
        for (Map.Entry<Integer, Boolean> entry:this.controllerPressed.entrySet()) {
            if (entry.getValue()) {
                entry.setValue(false); //change just pressed to pressed
            }
        }
}
}
