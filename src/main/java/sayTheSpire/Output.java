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
import sayTheSpire.ui.effects.EffectManager;
import sayTheSpire.events.Event;
import sayTheSpire.events.EventManager;
import sayTheSpire.map.BaseRoomNode;
import sayTheSpire.speech.SpeechManager;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.localization.LocalizationManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.input.InputManager;
import sayTheSpire.ui.mod.MapManager;
import sayTheSpire.ui.mod.UIManager;
import sayTheSpire.ui.navigators.TreeNavigator;
import sayTheSpire.buffers.*;

public class Output {

    public static String modVersion = "0.5.0-beta";

    private static Logger logger = LogManager.getLogger(Output.class.getName());

    public static BufferManager buffers = new BufferManager();
    public static Boolean tolkSetup = false;
    public static Boolean shouldInterruptSpeech = false;
    public static EffectManager effects = null;
    public static InputManager inputManager = null;
    public static MapManager mapManager = null;
    public static UIManager uiManager = null;
    public static SpeechManager speechManager = null;
    public static LocalizationManager localization;
    public static String eventText = null;
    public static UIElement currentUI = null;
    public static STSConfig config = null;

    public static void announceVersion() {
        Output.text(localization.localize("misc.versionString", "version", modVersion), false);
    }

    public static void preSetup() {
        localization = new LocalizationManager();
    }

    public static void postSetup() {

        localization.postSetupLoad();

        UIRegistry.setup();
        speechManager = new SpeechManager();

        // Effects manager
        effects = new EffectManager();
        inputManager = new InputManager();

        // initialize config
        try {
            config = new STSConfig();
            inputManager.fromJson(config.getInputObj());
            config.save();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        mapManager = new MapManager();
        uiManager = new UIManager(inputManager, mapManager);
        speechManager.setup();
        tolkSetup = true;

        setupBuffers();
    }

    private static void setupBuffers() {
        // create buffers
        BufferControls.buffers = buffers;
        buffers.add(new Buffer("unknown", "UI"));
        buffers.add(new Buffer("unknown", "UI extra"));
        buffers.add(new LeaderboardBuffer());
        buffers.add(new CardBuffer("current card"));
        buffers.add(new CardBuffer("upgrade preview", true));
        buffers.add(new EventBuffer("events"));
        buffers.add(new PlayerBuffer());
        buffers.add(new OrbBuffer("orb"));
        buffers.add(new MonsterBuffer("monster"));
        buffers.add(new PotionBuffer("potion"));
        buffers.add(new BlightBuffer("blight"));
        buffers.add(new RelicBuffer("relic"));
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
        if (text == null) {
            speechManager.output("Warning: output null text, report to mod dev", shouldInterruptSpeech && interrupt);
            return;
        }
        speechManager.output(text, shouldInterruptSpeech && interrupt);
        shouldInterruptSpeech = interrupt;
    }

    public static void textLocalized(String path, Boolean interrupt, LocalizationContext context) {
        text(context.localize(path), interrupt);
    }

    public static void textLocalized(String path, Boolean interrupt, Object... variables) {
        LocalizationContext context = localization.getContext("");
        int length = variables.length;
        if (length % 2 == 0) {
            for (int i = 0; i < length; i += 2) {
                String name = (String) variables[i];
                Object value = variables[i + 1];
                context.put(name, value);
            }
        }
        text(context.localize(path), interrupt);
    }

    public static Boolean getAllowVirtualInput() {
        if (uiManager == null)
            return false;
        return uiManager.getAllowVirtualInput() && config.getBoolean("input.virtual_input");
    }

    public static void setupBuffers(MapRoomNode node, Boolean isHovered, Boolean shouldAnnounce) {
        // This needs to be changed later
        InfoControls.bufferContext = "map";
        mapManager.handleNode(node, isHovered, shouldAnnounce);
    }

    public static void setUI(UIElement element) {
        InfoControls.bufferContext = "buffers";
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
        InfoControls.bufferContext = "buffers";
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        buffer.addMany(contents);
        buffers.enableBuffer("UI", true);
        BufferControls.setCurrentBuffer("UI");
    }

    public static void setupUIBufferMany(String... contents) {
        InfoControls.bufferContext = "buffers";
        Buffer buffer = buffers.getBuffer("UI");
        buffer.clear();
        for (String s : contents) {
            if (s != null)
                buffer.add(s);
        }
        buffers.enableBuffer("UI", true);
        BufferControls.setCurrentBuffer("UI");
    }

    public static void update() {
        MiscTriggers.update();
        EventManager.update();
        UIRegistry.update();
    }
}
