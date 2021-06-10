package sayTheSpire.ui.mod;

import java.util.ArrayList;
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

public class InputManager {
    private static final Logger logger = LogManager.getLogger(InputManager.class.getName());
    private ArrayList<Context> contexts;
    private HashMap<String, InputAction> actions;
    private HashMap<Integer, Boolean> controllerPressed, keyboardPressed;
    private HashSet<Integer> keysToCheck;
    private EnumSet<InputAction.Modifiers> keyboardModifiers;

    //We need this to prevent unintentional controller actions registering on context change
    private Boolean temporaryInputHalt = false;

    public InputManager() {
        this.contexts = new ArrayList();
    this.actions = new HashMap();
    this.setupActions();
    this.keysToCheck = new HashSet();
    this.determineKeysToCheck();
    this.pushContext(new GameContext());
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

    public void emitAction(InputAction action, String reason) {
        for (Context context:this.contexts) {
            Boolean result = false;
            if (reason.equals("justPressed")) result = context.onJustPress(action);
            else if (reason.equals("pressed")) result = context.onPress(action);
            else if (reason.equals("justReleased")) result = context.onJustRelease(action);
            else throw new RuntimeException("Invalud emit action " + reason + " for " + action.getName());
            if (result == true) { //input stopped
                break;
            }
        }
    }

    public Boolean getAllowVirtualInput() {
        Context current = this.getCurrentContext();
        if (current != null) {
            return current.getAllowVirtualInput();
        }
        return false;
    }

    public Context getCurrentContext() {
        if (contexts.size() <= 0) {
            return null;
        }
        return this.contexts.get(0);
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

    public void pushContext(Context context) {
            if (contexts.size() > 0) {
            contexts.get(0).onUnfocus();
        }
    this.clearActionStates();
        this.contexts.add(0, context);
        context.onFocus();
        this.temporaryInputHalt = true;
    }

    public void popContext() {
        if (this.contexts.size() > 0) {
            Context context = this.contexts.get(0);
            context.onUnfocus();
            this.contexts.remove(0);
        }
        this.clearActionStates();
        this.temporaryInputHalt = true;
        if (this.contexts.size() > 0) {
            this.contexts.get(0).onFocus();
        }
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

    public void updateLast() {
        if (this.temporaryInputHalt) {
            this.temporaryInputHalt = false;
            return;
        }
        if (!Output.config.getBoolean("input.virtual_input")) return;
Context current  = this.getCurrentContext();
    if (current.getShouldForceControllerMode()) {
        Settings.isControllerMode = true;
    }
        updateKeyboardState();
        for (InputAction action:this.actions.values()) {
            action.update();
            int keycode = action.getControllerKeycode();
            if (this.isControllerJustPressed(keycode)) {
                this.controllerPressed.put(keycode, false);
            }
        }
    }
}
