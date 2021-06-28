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
    private EnumSet<InputAction.Modifiers> keyboardModifiers;
    UIManager uiManager;

    public InputManager(UIManager uiManager) {
        this.uiManager = uiManager;
    this.actions = new HashMap();
    this.setupActions();
    this.keysToCheck = new HashSet();
    this.determineKeysToCheck();
    this.controllerPressed = new HashMap();
    this.keyboardPressed = new HashMap();
    this.keyboardModifiers = EnumSet.noneOf(InputAction.Modifiers.class);
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
        if (this.keyboardModifiers != null) this.keyboardModifiers.clear();
        for (InputAction action:this.actions.values()) {
            action.clearStates();
        }
    }

    public void determineKeysToCheck() {
        keysToCheck.clear();
        for (InputAction action:this.actions.values()) {
            Integer key = action.getKeyboardKey();
            if (key == null) continue; //not assigned
                            keysToCheck.add(key);
        }
    }

    public void handleControllerKeycodePress(int keycode) {
        this.controllerPressed.put(keycode, true);
    }

    public void handleControllerKeycodeRelease(int keycode) {
        this.controllerPressed.remove(keycode);
    }

    public Boolean isControllerJustPressed(int keycode) {
        return this.controllerPressed.getOrDefault(keycode, false);
    }

    public Boolean isControllerPressed(int keycode) {
        return this.controllerPressed.containsKey(keycode);
    }

    public Boolean isKeyboardJustPressed(InputAction action) {
        EnumSet<InputAction.Modifiers> actionModifiers = action.getKeyboardModifiers();
        Integer actionKey = action.getKeyboardKey();
        if (actionModifiers == null || actionKey == null) return false;
        return actionModifiers.equals(this.keyboardModifiers) && this.keyboardPressed.getOrDefault(actionKey, false);
    }

    public Boolean isKeyboardPressed(InputAction action) {
        EnumSet<InputAction.Modifiers> actionModifiers = action.getKeyboardModifiers();
        Integer actionKey = action.getKeyboardKey();
        if (actionModifiers == null || actionKey == null) return false;
        return actionModifiers.equals(this.keyboardModifiers) && this.keyboardPressed.containsKey(actionKey);
    }

    private void setupActions() {
        String actionNames[] = {"select", "cancel", "top panel", "proceed", "peek", "page left", "page right", "draw pile", "discard pile", "map", "settings", "up", "down", "left", "right", "alt up", "alt down", "alt left", "alt right", "inspect up", "inspect down", "inspect left", "inspect right"};
        for (String name:actionNames) {
            this.addAction(new InputAction(name, this));
        }
    }

    private void updateKeyboardModifiers() {
        this.keyboardModifiers.clear();
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) this.keyboardModifiers.add(InputAction.Modifiers.CONTROL);
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) this.keyboardModifiers.add(InputAction.Modifiers.SHIFT);
        if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) this.keyboardModifiers.add(InputAction.Modifiers.ALT);
    }

    public void updateKeyboardState() {
        updateKeyboardModifiers();
        for (Integer key:keysToCheck) {
            if (Gdx.input.isKeyJustPressed(key)) {
                keyboardPressed.put(key, true);
            } else if (Gdx.input.isKeyPressed(key)) {
                keyboardPressed.put(key, false);
            } else {
                keyboardPressed.remove(key);
            }
        }
    }

    public void updateFirst() {
        if (!Output.config.getBoolean("input.virtual_input")) return;
Context current  = this.uiManager.getCurrentContext();
    if (current.getShouldForceControllerMode()) {
        Settings.isControllerMode = true;
    }
        updateKeyboardState();
        for (InputAction action:this.actions.values()) {
            action.update();
        }
    }

    public void updateLast() {
                if (!Output.config.getBoolean("input.virtual_input")) return;
        for (Map.Entry<Integer, Boolean> entry:this.controllerPressed.entrySet()) {
            if (entry.getValue()) {
                entry.setValue(false);
            }
        }
}
}
