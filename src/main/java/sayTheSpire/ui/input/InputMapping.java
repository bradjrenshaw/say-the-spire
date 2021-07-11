package sayTheSpire.ui.input;

import java.util.EnumSet;
import java.util.HashSet;
import com.badlogic.gdx.Input.Keys;


public class InputMapping {

    public enum Modifiers {
        CONTROL,
        SHIFT,
        ALT
    };

    private String actionName;
    private String inputType;
    private EnumSet modifiers;
    private int keycode;
    private Boolean isDefault;

    public InputMapping(String actionName, String inputType, Boolean isDefault, EnumSet modifiers, int keycode) {
        this.actionName = actionName;
        this.inputType = inputType;
        this.isDefault = isDefault;
        this.modifiers = modifiers;
        this.keycode = keycode;
    }

    public InputMapping(String actionName, String inputType, Boolean isDefault, HashSet<Integer> keycodes) {
        this.actionName = actionName;
        this.inputType = inputType;
        this.isDefault = isDefault;
                switch(inputType) {
            case "keyboard":
            this.modifiers = EnumSet.noneOf(Modifiers.class);
            this.keycode = -4000; //hacky
            for (int testCode:keycodes) {
                if (testCode == Keys.CONTROL_LEFT || testCode == Keys.CONTROL_RIGHT) this.modifiers.add(Modifiers.CONTROL);
                else if (testCode == Keys.SHIFT_LEFT || testCode == Keys.SHIFT_RIGHT) this.modifiers.add(Modifiers.SHIFT);
                else if (testCode == Keys.ALT_LEFT || testCode == Keys.ALT_RIGHT) this.modifiers.add(Modifiers.ALT);
                else {
                    if (this.keycode != -4000) throw new RuntimeException("Tried to assign multiple non-modifier keycodes to keyboard InputMapping.");
                    this.keycode = testCode;
                }
            }
            break;
            case "controller":
            if (keycodes.size() != 1) {
                throw new RuntimeException("Tried to assign incorrect number of keycodes to controller mapping.");
            }
            this.keycode = (int)keycodes.toArray()[0];
            this.modifiers = EnumSet.noneOf(Modifiers.class);
            break;
            default:
            throw new RuntimeException("Invalid InputMapping type " + inputType + ".");
        }
    }

    public InputMapping(String actionName, String inputType, Boolean isDefault, int keycode) {
        this(actionName, inputType, isDefault, EnumSet.noneOf(Modifiers.class), keycode);
    }

    public String getActionName() {
        return this.actionName;
    }

    public String getInputType() {
        return this.inputType;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public int getKeycode() {
        return this.keycode;
    }

    public EnumSet getModifiers() {
        return this.modifiers;
    }
}
