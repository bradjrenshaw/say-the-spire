package sayTheSpire.ui.mod;

import java.util.ArrayList;
import java.util.HashMap;


public class InputManager {

    private ArrayList<Context> contexts;
    private HashMap<String, InputAction> actions;

    public InputManager() {
        this.contexts = new ArrayList();
    this.actions = new HashMap();
    this.setupActions();
    this.pushContext(new GameContext());
    }

    public void addAction(InputAction action) {
        String name = action.getName();
        if (this.actions.containsKey(name)) {
            throw new RuntimeException("Error: InputManager: An action with name " + name + " already exists.");
        }
        this.actions.put(name, action);
    }

    public void emitAction(InputAction action, String reason) {
        System.out.println("Emiting action " + action.getName() + " with reason " + reason);
        for (Context context:this.contexts) {
            Boolean result = false;
            if (reason.equals("justPressed")) result = context.onJustPress(action);
            else if (reason.equals("justReleased")) result = context.onJustReleased(action);
            else throw new RuntimeException("Invalud action for " + action.getName());
            if (result) { //input stopped
                break;
            }
        }
    }

    public void handleKeycodePress(int keycode, Boolean isController) {
        if (!isController) return; //not yet
        for (InputAction action:this.actions.values()) {
            if (isController && keycode == action.getControllerKeycode()) {
                action.press();
                this.emitAction(action, "justPressed");
            }
        }
    }

    public void handleKeycodeRelease(int keycode, Boolean isController) {
        if (!isController) return; //not yet
        for (InputAction action:this.actions.values()) {
            if (isController && keycode == action.getControllerKeycode()) {
                action.release();
                this.emitAction(action, "justReleased");
            }
        }
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
        String actionNames[] = {"select", "cancel", "top panel", "proceed", "peek", "page left", "page right", "map", "settings", "up", "down", "left", "right", "alt up", "alt down", "alt left", "alt right", "inspect up", "inspect down", "inspect left", "inspect right"};
        for (String name:actionNames) {
            this.addAction(new InputAction(name));
        }
    }

    public void updateLast() {
        for (InputAction action:this.actions.values()) {
            action.clearJust();
        }
    }
}
