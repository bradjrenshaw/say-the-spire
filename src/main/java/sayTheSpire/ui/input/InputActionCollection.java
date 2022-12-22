package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.badlogic.gdx.Input.Keys;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Manages stored input mappings as well as assigning those and default mappings to actions */
public class InputActionCollection {

    private static final Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");
    private InputManager inputManager;
    private HashMap<String, InputAction> actions;
    private HashMap<String, ArrayList<InputMapping>> defaults;

    public InputActionCollection(InputManager manager) {
        this.inputManager = manager;
        this.actions = new HashMap();
        this.defaults = this.getDefaults();
        this.setupActions(null);
    }

    public InputActionCollection(InputManager manager, JsonObject input) {
        this(manager);
        this.setupActions(input);
    }

    private void setupActions(JsonObject input) {
        for (String name : InputManager.actionNames) {
            JsonArray mappingsArray = null;
            if (input != null && input.has(name))
                mappingsArray = input.getAsJsonArray(name);
            InputAction action = null;
            if (mappingsArray != null) {
                action = new InputAction(name, this.inputManager, mappingsArray);
            } else {
                action = new InputAction(name, this.inputManager);
                action.setMappings(this.defaults.get(name));
            }
            this.actions.put(name, action);
        }
    }

    private void buildKeyboardDefaults(MappingBuilder builder) {
        builder.setDefaultInputType("keyboard").action("select").mapping(Keys.ENTER).action("cancel")
                .mapping(Keys.ESCAPE).action("proceed").mapping(Keys.E).action("top panel").mapping(Keys.T)
                .action("peek").mapping(Keys.X).action("page left").mapping(Keys.D).action("page right").mapping(Keys.F)
                .action("draw pile").mapping(Keys.Q).action("discard pile").mapping(Keys.W).action("map")
                .mapping(Keys.M).action("settings").mapping(Keys.BACKSPACE).action("up").mapping(Keys.UP).action("down")
                .mapping(Keys.DOWN).action("left").mapping(Keys.LEFT).action("right").mapping(Keys.RIGHT)
                .action("inspect up").mapping(Keys.CONTROL_LEFT, Keys.UP).action("inspect down")
                .mapping(Keys.CONTROL_LEFT, Keys.DOWN).action("inspect left").mapping(Keys.CONTROL_LEFT, Keys.LEFT)
                .action("inspect right").mapping(Keys.CONTROL_LEFT, Keys.RIGHT).action("read player block")
                .mapping(Keys.CONTROL_LEFT, Keys.B).action("read player energy").mapping(Keys.CONTROL_LEFT, Keys.Y)
                .action("read player gold").mapping(Keys.CONTROL_LEFT, Keys.G).action("read player hp")
                .mapping(Keys.CONTROL_LEFT, Keys.H).action("read summarized intents").mapping(Keys.CONTROL_LEFT, Keys.I)
                .action("read detailed intents").mapping(Keys.ALT_LEFT, Keys.I).action("read act boss")
                .mapping(Keys.CONTROL_LEFT, Keys.N).action("read player powers").mapping(Keys.CONTROL_LEFT, Keys.P);
    }

    private void buildControllerDefaults(MappingBuilder builder) {
        builder.setDefaultInputType("controller").action("select").mapping(controllerPrefs.getInteger("SELECT", 0))
                .action("cancel").mapping(controllerPrefs.getInteger("CANCEL", 1)).action("top panel")
                .mapping(controllerPrefs.getInteger("VIEW", 2)).action("proceed")
                .mapping(controllerPrefs.getInteger("PROCEED", 3)).action("peek")
                .mapping(controllerPrefs.getInteger("PEEK", 8)).action("page left")
                .mapping(controllerPrefs.getInteger("PAGE_LEFT_KEY", 4)).action("page right")
                .mapping(controllerPrefs.getInteger("PAGE_RIGHT_KEY", 5)).action("draw pile")
                .mapping(controllerPrefs.getInteger("DRAW_PILE", 1004)).action("discard pile")
                .mapping(controllerPrefs.getInteger("DISCARD_PILE", -1004)).action("map")
                .mapping(controllerPrefs.getInteger("MAP", 6)).action("settings")
                .mapping(controllerPrefs.getInteger("SETTINGS", 7)).action("up")
                .mapping(controllerPrefs.getInteger("LS_Y_POSITIVE", -1000)).action("down")
                .mapping(controllerPrefs.getInteger("LS_Y_NEGATIVE", 1000)).action("left")
                .mapping(controllerPrefs.getInteger("LS_X_NEGATIVE", -1001)).action("right")
                .mapping(controllerPrefs.getInteger("LS_X_POSITIVE", 1001)).action("inspect up")
                .mapping(controllerPrefs.getInteger("RS_Y_POSITIVE", -1002)).action("inspect down")
                .mapping(controllerPrefs.getInteger("RS_Y_NEGATIVE", 1002)).action("inspect left")
                .mapping(controllerPrefs.getInteger("RS_X_POSITIVE", -1003)).action("inspect right")
                .mapping(controllerPrefs.getInteger("RS_X_NEGATIVE", 1003)).action("alt up")
                .mapping(controllerPrefs.getInteger("D_NORTH", -2000)).action("alt down")
                .mapping(controllerPrefs.getInteger("D_SOUTH", 2000)).action("alt left")
                .mapping(controllerPrefs.getInteger("D_WEST", -2001)).action("alt right")
                .mapping(controllerPrefs.getInteger("D_EAST", 2001));
    }

    private HashMap<String, ArrayList<InputMapping>> getDefaults() {
        MappingBuilder builder = new MappingBuilder();
        builder.setIsDefault(true);
        this.buildKeyboardDefaults(builder);
        this.buildControllerDefaults(builder);
        return builder.toHashMap();
    }

    public Collection<InputAction> getActions() {
        return this.actions.values();
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        for (InputAction action : this.actions.values()) {
            obj.add(action.getName(), action.toJsonElement());
        }
        return obj;
    }
}
