package sayTheSpire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import sayTheSpire.events.Event;
import sayTheSpire.events.EventManager;
import sayTheSpire.mapNavigator.MapNavigator;
import sayTheSpire.speech.ClipboardHandler;
import sayTheSpire.speech.SpeechManager;
import sayTheSpire.speech.TolkHandler;
import sayTheSpire.speech.TolkResourceHandler;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.input.InputManager;
import sayTheSpire.ui.mod.UIManager;
import sayTheSpire.buffers.*;

public class Output {

    public static String modVersion = "Beta 0.2.1";

    public enum Direction {
        NONE, UP, DOWN, LEFT, RIGHT
    };

    private static Logger logger = LogManager.getLogger(Output.class.getName());

    public static String bufferContext = "";
    public static BufferManager buffers = new BufferManager();
    public static Boolean tolkSetup = false;
    public static Boolean shouldInterruptSpeech = false;
    public static InputManager inputManager = null;
    public static UIManager uiManager = null;
    public static SpeechManager speechManager = null;
    public static String eventText = null;
    public static UIElement currentUI = null;
    public static STSConfig config = null;

    public static void announceVersion() {
        text("Using Say the Spire version " + modVersion, false);
    }

    public static void setup() {
        speechManager = new SpeechManager();
        speechManager.registerHandler(new TolkResourceHandler());
        speechManager.registerHandler(new TolkHandler());
        speechManager.registerHandler(new ClipboardHandler());
        speechManager.setup();
        tolkSetup = true;

        // create buffers
        BufferControls.buffers = buffers;
        buffers.add(new Buffer("UI"));
        buffers.add(new CardBuffer("current card"));
        buffers.add(new CardBuffer("upgrade preview", true));
        buffers.add(new EventBuffer("events"));
        buffers.add(new PlayerBuffer());
        buffers.add(new OrbBuffer("orb"));
        buffers.add(new MonsterBuffer("monster"));
        buffers.add(new PotionBuffer("potion"));
        buffers.add(new RelicBuffer("relic"));

        // Create MapNavigator
        MapControls.navigator = new MapNavigator();

        // initialize config
        try {
            config = new STSConfig();
            config.save();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        uiManager = new UIManager(config);
        inputManager = uiManager.getInputManager();
    }

    public static void shutdown() {
        try {
            config.save();
            speechManager.unload();
            if (config.getBoolean("resources.dispose_resource_files"))
                speechManager.disposeResources();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void event(Event event) {
        EventManager.add(event);
    }

    public static void forceSpeechInterrupt() {
        shouldInterruptSpeech = true;
    }

    public static void silenceSpeech() {
        speechManager.silence();
    }

    public static void text(String text, Boolean interrupt) {
        if (!tolkSetup) {
            setup();
        }
        speechManager.output(text, shouldInterruptSpeech && interrupt);
        shouldInterruptSpeech = interrupt;
    }

    public static Boolean getAllowVirtualInput() {
        if (uiManager == null)
            return false;
        return uiManager.getAllowVirtualInput() && config.getBoolean("input.virtual_input", false);
    }

    public static void infoControls(Direction direction) {
        if (bufferContext.equals(""))
            return;
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && bufferContext.equals("map")) {
            mapInfoControls(direction);
        } else {
            bufferInfoControls(direction);
        }
    }

    public static void bufferInfoControls(Direction direction) {
        switch (direction) {
        case UP:
            BufferControls.nextItem();
            break;
        case DOWN:
            BufferControls.previousItem();
            break;
        case LEFT:
            BufferControls.previousBuffer();
            break;
        case RIGHT:
            BufferControls.nextBuffer();
            break;
        }
    }

    public static void mapInfoControls(Direction direction) {
        switch (direction) {
        case UP:
            MapControls.followPath(true);
            break;
        case DOWN:
            MapControls.followPath(false);
            break;
        case LEFT:
            MapControls.changePathChoice(-1);
            break;
        case RIGHT:
            MapControls.changePathChoice(1);
            break;
        case NONE:
            return;
        default:
            text("it's a map", true);
        }
    }

    public static Direction getInfoDirection() {
        if (CInputActionSet.inspectUp.isJustPressed())
            return Direction.UP;
        else if (CInputActionSet.inspectDown.isJustPressed())
            return Direction.DOWN;
        else if (CInputActionSet.inspectLeft.isJustPressed())
            return Direction.LEFT;
        else if (CInputActionSet.inspectRight.isJustPressed())
            return Direction.RIGHT;
        else
            return Direction.NONE;
    }

    public static void setupBuffers(MapRoomNode node, Boolean viewing) {
        bufferContext = "map";
        if (viewing) {
            MapControls.navigator.handleViewingNode(node, true);
        } else {
            MapControls.navigator.handleFocusedNode(node);
        }
    }

    public static void setUI(UIElement element) {
        bufferContext = "buffers";
        buffers.setAllEnabled(false);
        String current = element.handleBuffers(buffers);
        currentUI = element;
        String focusString = element.getFocusString();
        text(focusString, false);
        if (current == null)
            return; // current buffer should be unchanged
        BufferControls.setCurrentBuffer(current);
    }

    public static void setupUIBuffer(ArrayList<String> contents) {
        bufferContext = "buffers";
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        buffer.addMany(contents);
        buffers.enableBuffer("UI", true);
        BufferControls.setCurrentBuffer("UI");
    }

    public static void setupUIBufferMany(String... contents) {
        bufferContext = "buffers";
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        for (String s : contents) {
            buffer.add(s);
        }
        buffers.enableBuffer("UI", true);
        BufferControls.setCurrentBuffer("UI");
    }

    public static void updateInfoControls() {
        if (CInputActionSet.inspectUp.isJustPressed()) {
            infoControls(Direction.UP);
        } else if (CInputActionSet.inspectRight.isJustPressed()) {
            infoControls(Direction.RIGHT);
        } else if (CInputActionSet.inspectDown.isJustPressed()) {
            infoControls(Direction.DOWN);
        } else if (CInputActionSet.inspectLeft.isJustPressed()) {
            infoControls(Direction.LEFT);
        }
    }

    public static void update() {
        MiscTriggers.update();
        EventManager.update();
        UIRegistry.update();
    }
}
