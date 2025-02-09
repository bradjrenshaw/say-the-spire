package sayTheSpire.ui.input;

import com.badlogic.gdx.Input.Keys;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;

public class InputBuilder {

    private static final Prefs controllerPrefs = SaveHelper.getPrefs("STSInputSettings_Controller");

    public static InputActionCollection buildBaseActionCollection() {
        InputActionCollection actions = new InputActionCollection();

        // base Slay the Spire actions
        // The ControllerPrefs allow retrieval of the game's default controller input for the given action
        actions.addAction("select").addKeyboardMapping(false, false, false, Keys.ENTER)
                .addControllerMapping(controllerPrefs.getInteger("SELECT", 0)).setRequired();

        actions.addAction("cancel").addKeyboardMapping(false, false, false, Keys.ESCAPE)
                .addControllerMapping(controllerPrefs.getInteger("CANCEL", 1)).setRequired();

        actions.addAction("top panel").addKeyboardMapping(false, false, false, Keys.T)
                .addControllerMapping(controllerPrefs.getInteger("VIEW", 2));

        actions.addAction("proceed").addKeyboardMapping(false, false, false, Keys.E)
                .addControllerMapping(controllerPrefs.getInteger("PROCEED", 3));

        actions.addAction("peek").addKeyboardMapping(false, false, false, Keys.X)
                .addControllerMapping(controllerPrefs.getInteger("PEEK", 8));

        actions.addAction("page left").addKeyboardMapping(false, false, false, Keys.D)
                .addControllerMapping(controllerPrefs.getInteger("PAGE_LEFT_KEY", 4));

        actions.addAction("page right").addKeyboardMapping(false, false, false, Keys.F)
                .addControllerMapping(controllerPrefs.getInteger("PAGE_RIGHT_KEY", 5));

        actions.addAction("draw pile").addKeyboardMapping(false, false, false, Keys.Q)
                .addControllerMapping(controllerPrefs.getInteger("DRAW_PILE", 1004));

        actions.addAction("discard pile").addKeyboardMapping(false, false, false, Keys.W)
                .addControllerMapping(controllerPrefs.getInteger("DISCARD_PILE", -1004));

        actions.addAction("map").addKeyboardMapping(false, false, false, Keys.M)
                .addControllerMapping(controllerPrefs.getInteger("MAP", 6));

        actions.addAction("settings").addKeyboardMapping(false, false, false, Keys.BACKSPACE)
                .addControllerMapping(controllerPrefs.getInteger("SETTINGS", 7));

        actions.addAction("up").addKeyboardMapping(false, false, false, Keys.UP)
                .addControllerMapping(controllerPrefs.getInteger("LS_Y_POSITIVE", -1000))
                .addControllerMapping(controllerPrefs.getInteger("D_NORTH", -2000)).setRequired();

        actions.addAction("down").addKeyboardMapping(false, false, false, Keys.DOWN)
                .addControllerMapping(controllerPrefs.getInteger("LS_Y_NEGATIVE", 1000))
                .addControllerMapping(controllerPrefs.getInteger("D_SOUTH", 2000)).setRequired();

        actions.addAction("left").addKeyboardMapping(false, false, false, Keys.LEFT)
                .addControllerMapping(controllerPrefs.getInteger("LS_X_NEGATIVE", -1001))
                .addControllerMapping(controllerPrefs.getInteger("D_WEST", -2001)).setRequired();

        actions.addAction("right").addKeyboardMapping(false, false, false, Keys.RIGHT)
                .addControllerMapping(controllerPrefs.getInteger("LS_X_POSITIVE", 1001))
                .addControllerMapping(controllerPrefs.getInteger("D_EAST", 2001)).setRequired();

        actions.addAction("inspect up").addKeyboardMapping(true, false, false, Keys.UP)
                .addControllerMapping(controllerPrefs.getInteger("RS_Y_POSITIVE", -1002));

        actions.addAction("inspect down").addKeyboardMapping(true, false, false, Keys.DOWN)
                .addControllerMapping(controllerPrefs.getInteger("RS_Y_NEGATIVE", 1002));

        actions.addAction("inspect left").addKeyboardMapping(true, false, false, Keys.LEFT)
                .addControllerMapping(controllerPrefs.getInteger("RS_X_POSITIVE", -1003));

        actions.addAction("inspect right").addKeyboardMapping(true, false, false, Keys.RIGHT)
                .addControllerMapping(controllerPrefs.getInteger("RS_X_NEGATIVE", 1003));

        // mod specific actions
        actions.addAction("mod menu").addKeyboardMapping(true, false, false, Keys.M).setRequired();

        actions.addAction("read player block").addKeyboardMapping(true, false, false, Keys.B);

        actions.addAction("read player energy").addKeyboardMapping(true, false, false, Keys.Y);

        actions.addAction("read player gold").addKeyboardMapping(true, false, false, Keys.G);

        actions.addAction("read player hp").addKeyboardMapping(true, false, false, Keys.H);

        actions.addAction("read summarized intents").addKeyboardMapping(true, false, false, Keys.I);

        actions.addAction("read detailed intents").addKeyboardMapping(false, false, true, Keys.I);

        actions.addAction("read act boss").addKeyboardMapping(true, false, false, Keys.N);

        actions.addAction("read player powers").addKeyboardMapping(true, false, false, Keys.P);

        return actions;
    }

}
