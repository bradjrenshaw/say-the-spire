package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.buffers.BufferManager;

public class CharacterButtonElement extends UIElement {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CharacterOption");

    public static final String[] TEXT = uiStrings.TEXT;

    private CharacterOption character;
    private String flavorText;

    public CharacterButtonElement(CharacterOption character) {
        super("character button");
        this.character = character;
        this.flavorText = (String) ReflectionHacks.getPrivate(character, CharacterOption.class, "flavorText");
        this.localization.put("health", ReflectionHacks.getPrivate(character, CharacterOption.class, "hp"));
        this.localization.put("gold", ReflectionHacks.getPrivate(character, CharacterOption.class, "gold"));
        this.localization.put("unlocksRemaining",
                ReflectionHacks.getPrivate(character, CharacterOption.class, "unlocksRemaining"));
        this.localization.put("maxAscension",
                ReflectionHacks.getPrivate(character, CharacterOption.class, "maxAscensionLevel"));
    }

    private String getUnlockText() {
        switch (this.character.c.chosenClass) {
        case THE_SILENT:
            return TEXT[1];
        case DEFECT:
            return TEXT[3];
        case WATCHER:
            return TEXT[10];
        default:
            return this.localization.localize("unknown unlock");
        }
    }

    public String handleBuffers(BufferManager buffers) {
        if (this.character.locked) {
            Output.setupUIBufferMany(TEXT[0], this.getUnlockText());
        } else {
            ArrayList<String> buffer = new ArrayList();
            buffer.add(this.character.c.title);
            buffer.add(this.localization.localize("buffer health"));
            buffer.add(this.localization.localize("buffer gold"));
            buffer.add(TextParser.parse(this.flavorText));
            buffer.add(this.localization.localize("buffer unlocks remaining"));
            buffer.add(this.localization.localize("buffer max ascension"));
            Output.setupUIBuffer(buffer);
        }
        return "UI";
    }

    public CharacterOption getCharacterOption() {
        return character;
    }

    public String getLabel() {
        if (this.character.locked) {
            return TEXT[0];
        }
        return this.character.c.title;
    }
}
