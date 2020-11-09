package sayTheSpire.ui.mod;

import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import basemod.ReflectionHacks;


public class InputAction {
    
    private static Prefs keyboardPrefs = SaveHelper.getPrefs("STSInputSettings");
    private static Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");
    
    
    private String name;
    private Boolean pressed, justPressed, justReleased;

    public InputAction(String name) {
        this.name = name;
        this.pressed = false;
        this.justPressed = false;
        this.justReleased = false;
    }

    public CInputAction getGameControllerAction() {
        int keycode = this.getGameControllerKeycode();
        for (CInputAction action:CInputHelper.actions) {
            if (keycode == action.keycode) {
                return action;
            }
        }
        return null;
    }

    public int getGameControllerKeycode() {
        switch (this.name) {
            case "select":
            return controllerPrefs.getInteger("SELECT", 0);
            case "cancel":
            return controllerPrefs.getInteger("CANCEL", 1);
            case "top panel":
            return controllerPrefs.getInteger("VIEW", 2);
            case "proceed":
            return controllerPrefs.getInteger("PROCEED", 3);
            case "peek":
            return controllerPrefs.getInteger("PEEK", 8);
            case "page left":
            return controllerPrefs.getInteger("PAGE_LEFT_KEY", 4);
            case "page right":
            return controllerPrefs.getInteger("PAGE_RIGHT_KEY", 5);
            case "map":
            return controllerPrefs.getInteger("MAP", 6);
            case "settings":
            return controllerPrefs.getInteger("SETTINGS", 7);
            case "up":
            return controllerPrefs.getInteger("LS_Y_POSITIVE", -1000);
            case "down":
            return controllerPrefs.getInteger("LS_Y_NEGATIVE", 1000);
            case "left":
            return controllerPrefs.getInteger("LS_X_NEGATIVE", -1001);
            case "right":
            return controllerPrefs.getInteger("LS_X_POSITIVE", 1001);
            case "inspect up":
            return controllerPrefs.getInteger("RS_Y_POSITIVE", -1002);
            case "inspect down":
            return controllerPrefs.getInteger("RS_Y_NEGATIVE", 1002);
            case "inspect left":
            return controllerPrefs.getInteger("RS_X_POSITIVE", -1003);
            case "inspect right":
            return controllerPrefs.getInteger("RS_X_NEGATIVE", 1003);
            case "alt up":
            return controllerPrefs.getInteger("D_NORTH", -2000);
            case "alt down":
            return controllerPrefs.getInteger("D_SOUTH", 2000);
            case "alt left":
            return controllerPrefs.getInteger("D_WEST", -2001);
            case "alt right":
            return controllerPrefs.getInteger("D_EAST", 2001);
            default:
            return -1;
        }
    }

    public int getControllerKeycode() {
        return this.getGameControllerKeycode();
    }

    public String getName() {
        return this.name;
    }

    public Boolean isPressed() {
        return this.pressed;
    }

    public Boolean isJustPressed() {
        return this.justPressed;
    }

    public Boolean isJustReleased() {
        return this.justReleased;
    }

    public void setGameControllerActionJustPressed(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        ReflectionHacks.setPrivate(gameAction, CInputAction.class, "justPressed", value);
    }

     public void setGameControllerActionPressed(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        ReflectionHacks.setPrivate(gameAction, CInputAction.class, "pressed", value);
    }

    public void setGameControllerActionJustReleased(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        ReflectionHacks.setPrivate(gameAction, CInputAction.class, "justReleased", value);
    }

    public void clearJust() {
        this.justPressed = false;
        this.justReleased = false;
    }

    public void press() {
        this.pressed = true;
        this.justPressed = true;
        this.justReleased = false;
    }

    public void release() {
        this.pressed = false;
        this.justPressed = false;
        this.justReleased = true;
    }
}
