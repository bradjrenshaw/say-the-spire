package sayTheSpire.ui.input;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import basemod.ReflectionHacks;
import sayTheSpire.Output;

public class InputAction {

    private static final Logger logger = LogManager.getLogger(InputAction.class.getName());

    private String key;
    private InputManager inputManager;
    private Boolean isJustPressed, isPressed, isJustReleased;
    private ArrayList<InputMapping> mappings, defaultMappings;
    private Boolean isActive;

    public InputAction(String key, InputManager inputManager, Boolean isActive) {
        this.key = key;
        this.mappings = new ArrayList();
        this.inputManager = inputManager;
        this.isJustPressed = false;
        this.isPressed = false;
        this.isJustReleased = false;
        this.isActive = isActive;
    }

    public InputAction(String key, InputManager inputManager) {
        this(key, inputManager, true);
    }

    public InputAction(String key, InputManager manager, ArrayList<InputMapping> defaultMappings, Boolean isActive) {
        this(key, manager, isActive);
        for (InputMapping mapping : defaultMappings) {
            this.addMapping(mapping.copy());
        }
    }

    public InputAction(String key, InputManager manager, ArrayList<InputMapping> defaultMappings) {
        this(key, manager, defaultMappings, true);
    }

    public void addMapping(InputMapping mapping) {
        this.mappings.add(mapping);
        if (this.isActive)
            this.inputManager.RegisterControllerMappingIfValid(mapping);
    }

    public void removeMapping(InputMapping mapping) {
        this.mappings.remove(mapping);
        if (this.isActive)
            this.inputManager.unregisterControllerMappingIfValid(mapping);
    }

    public InputAction addControllerMapping(int keycode) {
        ControllerInputMapping mapping = new ControllerInputMapping(this.getKey(), keycode);
        this.addMapping(mapping);
        return this;
    }

    public InputAction addKeyboardMapping(Boolean control, Boolean shift, Boolean alt, int keycode) {
        KeyboardInputMapping mapping = new KeyboardInputMapping(this.getKey(), control, shift, alt, keycode);
        this.addMapping(mapping);
        return this;
    }

    public InputMapping getInputMappingFromJson(JsonObject json) {
        String inputType = json.get("inputType").getAsString();
        switch (inputType) {
        case "keyboard":
            return new KeyboardInputMapping(json);
        case "controller":
            return new ControllerInputMapping(json);
        default:
            return null;
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
        switch (this.getKey()) {
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

    public String getKey() {
        return this.key;
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
            this.mappings.add(mapping.copy());
        }
    }

    private void updateStates() {
        for (InputMapping mapping : this.mappings) {
            mapping.updateFirst();
            if (!this.isPressed && mapping.isJustPressed()) {
                this.isJustPressed = true;
                this.isPressed = true;
                this.isJustReleased = false;
                return;
            } else if (this.isJustPressed && mapping.isPressed()) {
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

    public void updateFirst() {
        this.updateStates();
        if (this.isJustPressed()) {
            Output.uiManager.emitAction(this, "justPressed");
        } else if (this.isPressed()) {
            Output.uiManager.emitAction(this, "pressed");
        } else if (this.isJustReleased()) {
            Output.uiManager.emitAction(this, "justReleased");
        }
    }

    public void updateLast() {
        for (InputMapping mapping : this.mappings) {
            mapping.updateLast();
        }

    }

    public void fromJson(JsonArray mappings) {
        if (mappings == null)
            return;

        // Check all mappings are valid before committing to adding any
        ArrayList<InputMapping> tempMappings = new ArrayList();
        for (JsonElement mappingElement : mappings) {
            InputMapping mapping = this.getInputMappingFromJson(mappingElement.getAsJsonObject());
            if (mapping != null) {
                tempMappings.add(mapping);
            } else {
                // This probably means the input mapping config is irrepperably broken
                throw new RuntimeException("Invalid inputType for mapping.");
            }
        }

        // Remove defaults
        this.mappings.clear();
        for (InputMapping mapping : tempMappings) {
            this.addMapping(mapping);
        }
    }

    public JsonElement toJsonElement() {
        JsonArray mappingsArray = new JsonArray();
        for (InputMapping mapping : this.getMappings()) {
            mappingsArray.add(mapping.toJsonElement());
        }
        return mappingsArray;
    }

    public Boolean isUIAction() {
        switch (this.getKey()) {
        case "up":
        case "alt up":
        case "down":
        case "alt down":
        case "left":
        case "alt left":
        case "right":
        case "alt right":
        case "select":
        case "cancel":
            return true;
        }
        return false;
    }

    public InputAction copy(Boolean isActive) {
        InputAction newAction = new InputAction(this.getKey(), this.inputManager, isActive);
        for (InputMapping mapping : this.getMappings()) {
            newAction.addMapping(mapping);
        }
        return newAction;
    }

    public String getLabel() {
        return Output.localization.localize("input.actions." + this.getKey() + ".label");
    }

    public String getDescription() {
        return Output.localization.localize("input.actions." + this.getKey() + ".description");
    }
}
