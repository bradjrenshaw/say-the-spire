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
import sayTheSpire.ui.UIElement;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.input.InputManager;
import sayTheSpire.ui.mod.UIManager;
import sayTheSpire.buffers.*;

public class Output {

    public static String modVersion = "Beta 0.2.0";

    public enum Direction {
        NONE, UP, DOWN, LEFT, RIGHT
    };

    private static Logger logger = LogManager.getLogger(Output.class.getName());

    public static String bufferContext = "";
    public static AbstractCard hoveredCard = null;
    public static AbstractMonster hoveredMonster = null;
    public static BufferManager buffers = new BufferManager();
    public static Boolean tolkSetup = false;
    public static Boolean shouldInterruptSpeech = false;
    public static InputManager inputManager = null;
    public static UIManager uiManager = null;
    public static SpeechManager speechManager = null;
    public static MonsterGroup currentMonsterGroup = null;
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

    public static void setUI(UIElement element) {
        String current = element.handleBuffers(buffers);
        currentUI = element;
        StringBuilder sb = new StringBuilder();
        String label = element.getLabel();
        if (label != null)
            sb.append(label);
        String extras = element.getExtrasString();
        if (extras != null)
            sb.append(", " + extras);
        String type = element.getTypeString();
        if (type != null)
            sb.append(" " + type);
        String status = element.getStatusString();
        if (status != null)
            sb.append(" " + status);
        AbstractPosition position = element.getPosition();
        if (position != null && config.getBoolean("ui.read_positions")) {
            sb.append(". " + position.getPositionString());
        }
        text(sb.toString(), true);
        if (current == null)
            return; // current buffer should be unchanged
        BufferControls.setCurrentBuffer(current);
    }

    public static void setupBuffers(AbstractCard card) {
        buffers.getBuffer("current card").setObject(card);
        buffers.getBuffer("upgrade preview").setObject(card);
        setupBuffers("card");
    }

    public static void setupBuffers(AbstractMonster monster) {
        Buffer buffer = buffers.getBuffer("monster");
        buffer.setObject(monster);
        setupBuffers("monster");
    }

    public static void setupBuffers(AbstractOrb orb) {
        buffers.getBuffer("orb").setObject(orb);
        setupBuffers("orb");
    }

    public static void setupBuffers(AbstractPotion potion) {
        Buffer buffer = buffers.getBuffer("potion");
        buffer.setObject(potion);
        setupBuffers("potion");
    }

    public static void setupBuffers(AbstractRelic relic) {
        buffers.getBuffer("relic").setObject(relic);
        setupBuffers("relic");
    }

    public static void setupBuffers(MapRoomNode node, Boolean viewing) {
        setupBuffers("map");
        if (viewing) {
            MapControls.navigator.handleViewingNode(node, true);
        } else {
            MapControls.navigator.handleFocusedNode(node);
        }
    }

    public static void setupBuffers(String context) {
        bufferContext = context;
        buffers.setAllEnabled(false);
        switch (context) {
        case "card":
            buffers.enableBuffer("current card", true);
            buffers.enableBuffer("upgrade preview", true);
            BufferControls.setCurrentBuffer("current card");
            break;
        case "monster":
            buffers.enableBuffer("monster", true);
            BufferControls.setCurrentBuffer("monster");
            break;
        case "orb":
            buffers.enableBuffer("orb", true);
            BufferControls.setCurrentBuffer("orb");
            break;
        case "potion":
            buffers.enableBuffer("potion", true);
            BufferControls.setCurrentBuffer("potion");
            break;
        case "relic":
            buffers.enableBuffer("relic", true);
            BufferControls.setCurrentBuffer("relic");
            break;
        case "UI":
            buffers.enableBuffer("UI", true);
            BufferControls.setCurrentBuffer("UI");
            break;
        }
    }

    public static void setupUIBuffer(ArrayList<String> contents) {
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        buffer.addMany(contents);
        setupBuffers("UI");
    }

    public static void setupUIBufferMany(String... contents) {
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        for (String s : contents) {
            buffer.add(s);
        }
        setupBuffers("UI");
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
