package sayTheSpire.ui.input;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ControllerInputMapping extends InputMapping {

    private int keycode;
    private boolean isDown;
    private Boolean justPressed, pressed;

    public ControllerInputMapping(String actionName, int keycode) {
        super(actionName);
        this.keycode = keycode;
        this.isDown = false;
    }

    public ControllerInputMapping(JsonObject obj) {
        super(obj);
        int keycode = obj.get("keycode").getAsInt();
        this.keycode = keycode;
        this.isDown = false;
    }

    public InputMapping copy() {
        return new ControllerInputMapping(this.getActionName(), this.keycode);
    }

    public void handleKeycodePress(int keycode) {
        if (keycode == this.getKeycode()) {
            this.isDown = true;
        }
    }

    public void handleKeycodeRelease(int keycode) {
        if (keycode == this.getKeycode()) {
            this.isDown = false;
        }
    }

    public void updateFirst() {
        if (this.isDown && !this.pressed) {
            this.justPressed = true;
            this.pressed = true;
        } else if (this.isDown && this.justPressed) {
            this.justPressed = false;
        } else if (!this.isDown) {
            this.justPressed = false;
            this.pressed = false;
        }
    }

    public Boolean isJustPressed() {
        return this.justPressed;
    }

    public Boolean isPressed() {
        return this.pressed;
    }

    public String getInputType() {
        return "controller";
    }

    public int getKeycode() {
        return this.keycode;
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        obj.addProperty("actionName", this.getActionName());
        obj.addProperty("inputType", this.getInputType());
        obj.addProperty("keycode", this.getKeycode());
        return obj;
    }

}
