package sayTheSpire.ui.input;

import java.util.Arrays;
import java.util.EnumSet;    
import java.util.HashSet;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.badlogic.gdx.Input.Keys;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import basemod.ReflectionHacks;
import sayTheSpire.utils.MapBuilder;
import sayTheSpire.Output;

public class InputAction {

    private static final Logger logger = LogManager.getLogger(InputAction.class.getName());


    private static Prefs keyboardPrefs = SaveHelper.getPrefs("STSInputSettings");
    private static Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");
    
    public static enum Modifiers {CONTROL, SHIFT, ALT};
    private String name;
    private InputManager manager;
    private Boolean controllerPressed, controllerJustPressed, controllerJustReleased;
    private Boolean keyboardJustPressed, keyboardPressed, keyboardJustReleased;
    private static HashMap<String, Integer> defaultKeyboardKeys;
    private static HashMap<String, EnumSet<Modifiers>> defaultKeyboardModifiers;

    static {
        defaultKeyboardKeys = new MapBuilder()
        .put("select", Keys.ENTER)
        .put("cancel", Keys.BACKSPACE)
        .put("top panel", Keys.T)
        .put("proceed", Keys.E)
        .put("peek", Keys.X)
        .put("page left", Keys.D)
        .put("page right", Keys.F)
        .put("draw pile", Keys.Q)
        .put("discard pile", Keys.W)
        .put("map", Keys.M)
        .put("settings", Keys.ESCAPE)
        .put("up", Keys.UP)
        .put("down", Keys.DOWN)
        .put("left", Keys.LEFT)
        .put("right", Keys.RIGHT)
        .put("inspect up", Keys.UP)
        .put("inspect down", Keys.DOWN)
        .put("inspect left", Keys.LEFT)
        .put("inspect right", Keys.RIGHT)
        .toHashMap();
        defaultKeyboardModifiers = new MapBuilder()
        .put("inspect up", EnumSet.of(Modifiers.CONTROL))
        .put("inspect down", EnumSet.of(Modifiers.CONTROL))
        .put("inspect left", EnumSet.of(Modifiers.CONTROL))
        .put("inspect right", EnumSet.of(Modifiers.CONTROL))
        .toHashMap();
    }

    public InputAction(String name, InputManager manager) {
        this.name = name;
        this.manager = manager;
        this.controllerPressed = false;
        this.controllerJustPressed = false;
        this.controllerJustReleased = false;
        this.keyboardPressed = false;
        this.keyboardJustPressed = false;
        this.keyboardJustReleased = false;
    }

    public void clearStates() {
        this.controllerJustPressed = false;
        this.controllerPressed = false;
        this.controllerJustReleased = false;
        this.keyboardJustPressed = false;
        this.keyboardPressed = false;
        this.keyboardJustReleased = false;
        this.setGameControllerActionJustPressed(false);
        this.setGameControllerActionPressed(false);
        this.setGameControllerActionJustReleased(false);
    }

    public CInputAction getGameControllerAction() {
        int keycode = this.getGameControllerKeycode();
        for (CInputAction action:CInputHelper.actions) {
            if (keycode == action.keycode) {
                return action;
            }
        }
        Output.text("error: game action for " + this.getName() + " not found", false);
        return null;
    }

public Integer getDefaultKeyboardKey() {
    return defaultKeyboardKeys.getOrDefault(this.getName(), null);
}

    public EnumSet<Modifiers> getDefaultKeyboardModifiers() {
        return defaultKeyboardModifiers.getOrDefault(this.getName(), EnumSet.noneOf(Modifiers.class));
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
            case "draw pile":
            return controllerPrefs.getInteger("DRAW_PILE", 1004);
            case "discard pile":
            return controllerPrefs.getInteger("DISCARD_PILE", -1004);
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

    public Integer getKeyboardKey() {
        return this.getDefaultKeyboardKey();
    }

    public EnumSet<Modifiers> getKeyboardModifiers() {
        return this.getDefaultKeyboardModifiers();
    }

    public String getName() {
        return this.name;
    }

    public Boolean isPressed() {
        return this.keyboardPressed || this.controllerPressed;
    }

    public Boolean controllerIsJustPressed() {
        return this.controllerJustPressed;
    }

    public Boolean isJustPressed() {
        return this.keyboardJustPressed || this.controllerJustPressed;
    }

    public Boolean isJustReleased() {
        return this.keyboardJustReleased || this.controllerJustReleased;
    }

    public static HashSet<Integer> keySet(Integer... args) {
        return new HashSet<Integer>(Arrays.asList(args));
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

    private void updateControllerState() {
        int keycode = this.getControllerKeycode();
        Boolean managerJustPressed = this.manager.isControllerJustPressed(keycode);
        Boolean managerPressed = this.manager.isControllerPressed(keycode);
        if (managerJustPressed) {
            this.controllerJustPressed = true;
            this.controllerPressed = true;
            this.controllerJustReleased = false;
        } else if (managerPressed) {
            this.controllerJustPressed = false;
            this.controllerPressed = true;
            this.controllerJustReleased = false;
        } else {
            if (this.controllerJustReleased) {
                this.controllerJustReleased = false;
            } else if (this.controllerPressed && !managerPressed) {
                this.controllerJustReleased = true;
            }
            this.controllerJustPressed = false;
            this.controllerPressed = false;
        }
    }

    private void updateKeyboardState() {
                Boolean managerJustPressed = this.manager.isKeyboardJustPressed(this);
        Boolean managerPressed = this.manager.isKeyboardPressed(this);
        if (managerJustPressed) {
            this.keyboardJustPressed = true;
            this.keyboardPressed = true;
            this.keyboardJustReleased = false;
        } else if (managerPressed) {
            this.keyboardJustPressed = false;
            this.keyboardPressed = true;
            this.keyboardJustReleased = false;
        } else {
            if (this.keyboardJustReleased) {
                this.keyboardJustReleased = false;
            } else if (this.keyboardPressed && !managerPressed) {
                this.keyboardJustReleased = true;
            }
            this.keyboardJustPressed = false;
            this.keyboardPressed = false;
        }
    }

    public void update() {
        this.updateKeyboardState();
     this.updateControllerState();
        if (this.isJustPressed()) {
            this.manager.uiManager.emitAction(this, "justPressed");
        } else if (this.isPressed()) {
            this.manager.uiManager.emitAction(this, "pressed");
        } else if (this.isJustReleased()) {
            this.manager.uiManager.emitAction(this, "justReleased");
        }
    }
}
