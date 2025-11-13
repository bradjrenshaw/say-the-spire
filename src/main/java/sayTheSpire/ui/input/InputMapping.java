package sayTheSpire.ui.input;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.badlogic.gdx.Input.Keys;
import java.util.HashSet;

public abstract class InputMapping {

    private String actionName;
    private Boolean isDefault;

    public InputMapping(String actionName) {
        this.actionName = actionName;
    }

    public InputMapping(JsonObject obj) {
        String inputType = obj.get("inputType").getAsString();
        if (inputType != this.getInputType()) {
            throw new RuntimeException("Action input type of " + inputType + " Does not match correct mapping type of "
                    + this.getInputType());
        }
        String actionName = obj.get("actionName").getAsString();
        this.actionName = actionName;
    }

    public abstract InputMapping copy();

    public void updateFirst() {
        // Update first code goes here
    }

    public void updateLast() {
        // Update last code goes here
    }

    public abstract Boolean isJustPressed();

    public abstract Boolean isPressed();

    public String getActionName() {
        return this.actionName;
    }

    public abstract String getInputType();

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public abstract JsonElement toJsonElement();

    public String toString() {
        return this.getInputType();
    }

    public String getLabel() {
        return this.toString();
    }

}
