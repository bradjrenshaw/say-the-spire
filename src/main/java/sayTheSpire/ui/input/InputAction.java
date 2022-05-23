package sayTheSpire.ui.input;

import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sayTheSpire.Output;

public class InputAction {

    private static final Logger logger = LogManager.getLogger(InputAction.class.getName());

    private String name;
    private InputManager inputManager;
    private Boolean isJustPressed, isPressed, isJustReleased;
    private ArrayList<InputMapping> mappings;

    public InputAction(String name, InputManager inputManager) {
        this.name = name;
        this.mappings = new ArrayList();
        this.inputManager = inputManager;
        this.isJustPressed = false;
        this.isPressed = false;
        this.isJustReleased = false;
    }

    public InputAction(String name, InputManager manager, JsonArray mappings) {
        this(name, manager);
        for (JsonElement mappingElement : mappings) {
            InputMapping mapping = new InputMapping(mappingElement.getAsJsonObject());
            this.mappings.add(mapping);
        }
    }

    public void clearStates() {
        this.isJustPressed = false;
        this.isPressed = false;
        this.isJustReleased = false;
        this.setGameControllerActionJustPressed(false);
        this.setGameControllerActionPressed(false);
        this.setGameControllerActionJustReleased(false);
    }

    public CInputAction getGameControllerAction() {
        switch (this.name) {
        case "select":
            return CInputActionSet.select;
        case "cancel":
            return CInputActionSet.cancel;
        case "top panel":
            return CInputActionSet.topPanel;
        case "proceed":
            return CInputActionSet.proceed;
        case "peek":
            return CInputActionSet.peek;
        case "page left":
            return CInputActionSet.pageLeftViewDeck;
        case "page right":
            return CInputActionSet.pageRightViewExhaust;
        case "draw pile":
            return CInputActionSet.drawPile;
        case "discard pile":
            return CInputActionSet.discardPile;
        case "map":
            return CInputActionSet.map;
        case "settings":
            return CInputActionSet.settings;
        case "up":
            return CInputActionSet.up;
        case "down":
            return CInputActionSet.down;
        case "left":
            return CInputActionSet.left;
        case "right":
            return CInputActionSet.right;
        case "inspect up":
            return CInputActionSet.inspectUp;
        case "inspect down":
            return CInputActionSet.inspectDown;
        case "inspect left":
            return CInputActionSet.inspectLeft;
        case "inspect right":
            return CInputActionSet.inspectRight;
        case "alt up":
            return CInputActionSet.altUp;
        case "alt down":
            return CInputActionSet.altDown;
        case "alt left":
            return CInputActionSet.altLeft;
        case "alt right":
            return CInputActionSet.altRight;
        default:
            return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<InputMapping> getMappings() {
        return this.mappings;
    }

    public Boolean isPressed() {
        return this.isPressed;
    }

    public Boolean isJustPressed() {
        return this.isJustPressed;
    }

    public Boolean isJustReleased() {
        return this.isJustReleased;
    }

    public void setGameControllerActionJustPressed(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        if (gameAction != null)
            ReflectionHacks.setPrivate(gameAction, CInputAction.class, "justPressed", value);
    }

    public void setGameControllerActionPressed(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        if (gameAction != null)
            ReflectionHacks.setPrivate(gameAction, CInputAction.class, "pressed", value);
    }

    public void setGameControllerActionJustReleased(Boolean value) {
        CInputAction gameAction = this.getGameControllerAction();
        if (gameAction != null)
            ReflectionHacks.setPrivate(gameAction, CInputAction.class, "justReleased", value);
    }

    void setMappings(ArrayList<InputMapping> mappings) {
        for (InputMapping mapping : mappings) {
            this.mappings.add(new InputMapping(mapping));
        }
    }

    private void updateStates() {
        for (InputMapping mapping : this.mappings) {
            if (!this.isPressed && this.inputManager.isMappingJustPressed(mapping)) {
                this.isJustPressed = true;
                this.isPressed = true;
                this.isJustReleased = false;
                return;
            } else if (this.isJustPressed && this.inputManager.isMappingPressed(mapping)) {
                this.isJustPressed = false;
                this.isPressed = true;
                this.isJustReleased = false;
                return;
            }
        }

        // No mappings were detected as justPressed or pressed, so handle release
        if (this.isPressed) {
            this.isJustPressed = false;
            this.isPressed = false;
            this.isJustReleased = true;
        } else if (this.isJustReleased) {
            this.isJustReleased = false;
        }
    }

    public void update() {
        this.updateStates();
        if (this.isJustPressed()) {
            Output.uiManager.emitAction(this, "justPressed");
        } else if (this.isPressed()) {
            Output.uiManager.emitAction(this, "pressed");
        } else if (this.isJustReleased()) {
            Output.uiManager.emitAction(this, "justReleased");
        }
    }

    public JsonElement toJsonElement() {
        JsonArray mappingsArray = new JsonArray();
        for (InputMapping mapping : this.getMappings()) {
            mappingsArray.add(mapping.toJsonElement());
        }
        return mappingsArray;
    }
}
