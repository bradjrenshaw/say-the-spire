package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.ToggleButton;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class SettingsToggleButtonElement extends ToggleButtonElement {

    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Options Tip");

    public static final String[] MSG = tutorialStrings.TEXT;

    public static final String[] LABEL = tutorialStrings.LABEL;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");

    public static final String[] TEXT = uiStrings.TEXT;

    private ToggleButton button;
    private ToggleButton.ToggleBtnType type;

    public SettingsToggleButtonElement(ToggleButton button) {
        super();
        this.button = button;
        this.type = (ToggleButton.ToggleBtnType) ReflectionHacks.getPrivate(button, ToggleButton.class, "type");
        this.setStatusTexts(this.localization.localize(".ui.status.on"), this.localization.localize(".ui.status.off"));
    }

    public String handleBuffers(BufferManager buffers) {
        Descriptor descriptor = this.getDescriptor();
        Output.setupUIBuffer(descriptor.getUIBufferContents());
        return null;
    }

    public void onToggle() {
        Output.text(this.getStatusString(), true);
    }

    public ToggleButton getButton() {
        return this.button;
    }

    public Descriptor getDescriptor() {
        String fullscreenStrings[] = TextParser.parse(TEXT[4]).split("\n");
        String ambienceOptions[] = TextParser.parse(TEXT[7]).split("\n");
        String preferenceOptions[] = TextParser.parse(TEXT[9]).split("\n");
        String vsyncLabel = TextParser.parse(TEXT[17]).split("\n")[2];
        switch (this.type) {
        case FULL_SCREEN:
            return new Descriptor(fullscreenStrings[0], LABEL[1], MSG[1]);
        case W_FULL_SCREEN:
            return new Descriptor(fullscreenStrings[1], LABEL[2], MSG[2]);
        case SCREEN_SHAKE:
            return new Descriptor(fullscreenStrings[2]);
        case AMBIENCE_ON:
            return new Descriptor(ambienceOptions[0]);
        case MUTE_IF_BG:
            return new Descriptor(ambienceOptions[1]);
        case SUM_DMG:
            return new Descriptor(preferenceOptions[0]);
        case BLOCK_DMG:
            return new Descriptor(preferenceOptions[1]);
        case HAND_CONF:
            return new Descriptor(preferenceOptions[2]);
        case FAST_MODE:
            return new Descriptor(TEXT[10]);
        case UPLOAD_DATA:
            return new Descriptor(TEXT[14], TEXT[14], MSG[0]);
        case V_SYNC:
            return new Descriptor(vsyncLabel);
        case PLAYTESTER_ART:
            return new Descriptor(TEXT[18]);
        case SHOW_CARD_HOTKEYS:
            return new Descriptor(TEXT[19]);
        case EFFECTS:
            return new Descriptor(TEXT[21]);
        case LONG_PRESS:
            return new Descriptor(TEXT[25]);
        case BIG_TEXT:
            return new Descriptor(TEXT[26]);
        default:
            return new Descriptor(this.localization.localize("unknownType"));
        }
    }

    public String getLabel() {
        return TextParser.parse(this.getDescriptor().getKey());
    }

    public Boolean getEnabled() {
        return this.button.enabled;
    }
}
