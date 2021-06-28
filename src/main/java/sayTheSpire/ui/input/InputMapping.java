package sayTheSpire.input;

import java.util.HashSet;

public class InputMapping {
    private String actionName;
    private String inputType;
    private HashSet<Integer> keycodes;

    public InputMapping(String actionName, String inputType, HashSet<Integer> keycodes) {
        this.actionName = actionName;
        this.inputType = inputType;
        this.keycodes = keycodes;
    }

    public String getActionName() {
        return this.actionName;
    }

    public String getInputType() {
        return this.inputType;
    }

    public HashSet<Integer> getKeycodes() {
        return this.keycodes;
    }
}
