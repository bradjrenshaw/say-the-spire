package sayTheSpire.ui.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class KeyboardInputMapping extends InputMapping {

    private Boolean modControl, modShift, modAlt;
    private int keycode;

    public KeyboardInputMapping(String actionName, Boolean modControl, Boolean modShift, Boolean modAlt, int keycode) {
        super(actionName);
        this.modControl = modControl;
        this.modShift = modShift;
        this.modAlt = modAlt;
        this.keycode = keycode;
    }

    public KeyboardInputMapping(JsonObject obj) {
        super(obj);
        this.modControl = obj.get("modControl").getAsBoolean();
        this.modShift = obj.get("modShift").getAsBoolean();
        this.modAlt = obj.get("modAlt").getAsBoolean();
        this.keycode = obj.get("keycode").getAsInt();
    }

    private int getKeycode() {
        return this.keycode;
    }

    private Boolean modifiersPressed() {
        Boolean controlPressed = Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)
                || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT);
        Boolean shiftPressed = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT);
        Boolean altPressed = Gdx.input.isKeyPressed(Keys.ALT_LEFT) || Gdx.input.isKeyJustPressed(Keys.ALT_RIGHT);
        return this.modControl == controlPressed && this.modShift == shiftPressed && this.modAlt == altPressed;
    }

    public InputMapping copy() {
        return new KeyboardInputMapping(this.getActionName(), this.modControl, this.modShift, this.modAlt,
                this.keycode);
    }

    public Boolean isJustPressed() {
        return this.modifiersPressed() && Gdx.input.isKeyJustPressed(this.getKeycode());
    }

    public Boolean isPressed() {
        return this.modifiersPressed() && Gdx.input.isKeyPressed(this.getKeycode());
    }

    public String getInputType() {
        return "keyboard";
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        obj.addProperty("actionName", this.getActionName());
        obj.addProperty("inputType", this.getInputType());
        obj.addProperty("modControl", this.modControl);
        obj.addProperty("modShift", this.modShift);
        obj.addProperty("modAlt", this.modAlt);
        obj.addProperty("keycode", this.getKeycode());
        return obj;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.modControl)
            sb.append(Keys.toString(Keys.CONTROL_LEFT));
        if (this.modShift)
            sb.append(Keys.SHIFT_LEFT);
        if (this.modAlt)
            sb.append(Keys.ALT_LEFT);
        sb.append(Keys.toString(this.keycode));
        return super.toString() + sb.toString();
    }

}
