package sayTheSpire.ui.mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sayTheSpire.Output;

public class InputManager {
    private static final Logger logger = LogManager.getLogger(InputManager.class.getName());

    private ArrayList<Context> contexts;
    private HashMap<String, InputAction> actions;
    private HashMap<Integer, Boolean> controllerPressed;



    public InputManager() {
        this.contexts = new ArrayList();
    this.actions = new HashMap();
    this.setupActions();
    this.pushContext(new GameContext());
    this.controllerPressed = new HashMap();
    }

    public void addAction(InputAction action) {
        String name = action.getName();
        if (this.actions.containsKey(name)) {
            throw new RuntimeException("Error: InputManager: An action with name " + name + " already exists.");
        }
        this.actions.put(name, action);
    }

    public void emitAction(InputAction action, String reason) {
        for (Context context:this.contexts) {
            Boolean result = false;
            if (reason.equals("justPressed")) result = context.onJustPress(action);
            else if (reason.equals("pressed")) result = context.onPress(action);
            else if (reason.equals("justReleased")) result = context.onJustRelease(action);
            else throw new RuntimeException("Invalud emit action " + reason + " for " + action.getName());
            if (result) { //input stopped
                break;
            }
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

    public void pushContext(Context context) {
        System.out.println("Context pushed");
        if (contexts.size() > 0) {
            contexts.get(0).onUnfocus();
        }
        this.contexts.add(0, context);
        context.onFocus();
    }

    public void popContext() {
        if (this.contexts.size() > 0) {
            Context context = this.contexts.get(0);
            context.onUnfocus();
            this.contexts.remove(0);
        }
        if (this.contexts.size() > 0) {
            this.contexts.get(0).onFocus();
        }
        System.out.println("Context popped");
    }

    private void setupActions() {
        String actionNames[] = {"select", "cancel", "top panel", "proceed", "peek", "page left", "page right", "draw pile", "discard pile", "map", "settings", "up", "down", "left", "right", "alt up", "alt down", "alt left", "alt right", "inspect up", "inspect down", "inspect left", "inspect right"};
        for (String name:actionNames) {
            this.addAction(new InputAction(name, this));
        }
    }

    public void updateLast() {
        if (!Output.config.getBoolean("input.virtual_input")) return;
        for (InputAction action:this.actions.values()) {
            action.update();
            int keycode = action.getControllerKeycode();
            if (this.isControllerJustPressed(keycode)) {
                this.controllerPressed.put(keycode, false);
            }
        }
    }
}
