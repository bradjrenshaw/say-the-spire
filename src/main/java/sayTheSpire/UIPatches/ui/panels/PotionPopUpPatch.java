import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

@SpirePatch(clz = PotionPopUp.class, method = "update")
public class PotionPopUpPatch {

    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack
            .getTutorialString("Potion Panel Tip");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PotionPopUp");

    public static final String[] TEXT = uiStrings.TEXT;

    public static final String[] MSG = tutorialStrings.TEXT;

    public static final String[] LABEL = tutorialStrings.LABEL;

    public static void Postfix(PotionPopUp __instance) {
        AbstractPotion potion = (AbstractPotion) ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "potion");
        Hitbox top = (Hitbox) ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "hbTop");
        Hitbox bottom = (Hitbox) ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "hbBot");
        if (top.justHovered) {
            String label = TEXT[1];
            if (potion.isThrown) {
                Output.setupUIBufferMany(LABEL[0], MSG[0]);
                label = TEXT[0];
            } else {
                Output.setupUIBufferMany(LABEL[1], TextParser.parse(MSG[1]));
            }
            Output.text(label + " button", true);
        } else if (bottom.justHovered) {
            String label = TEXT[2];
            Output.text(label + " button", true);
            Output.setupUIBufferMany(LABEL[2], TextParser.parse(MSG[2], "potion"));
        }
    }
}
