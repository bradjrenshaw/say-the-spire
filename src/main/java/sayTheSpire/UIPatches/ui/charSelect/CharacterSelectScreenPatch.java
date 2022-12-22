import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import sayTheSpire.Output;

public class CharacterSelectScreenPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CharacterSelectScreen");

    public static final String[] TEXT = uiStrings.TEXT;

    @SpirePatch(clz = CharacterSelectScreen.class, method = "updateAscensionToggle")
    public static class UpdateAscensionTogglePatch {

        public static void Postfix(CharacterSelectScreen __instance) {
            Hitbox ascensionModeHb = (Hitbox) ReflectionHacks.getPrivate(__instance, CharacterSelectScreen.class,
                    "ascensionModeHb");
            Hitbox ascLeftHb = (Hitbox) ReflectionHacks.getPrivate(__instance, CharacterSelectScreen.class,
                    "ascLeftHb");
            Hitbox ascRightHb = (Hitbox) ReflectionHacks.getPrivate(__instance, CharacterSelectScreen.class,
                    "ascRightHb");
            if (ascensionModeHb.clicked || CInputActionSet.proceed.isJustPressed()) {
                if (__instance.isAscensionMode) {
                    Output.text(TEXT[8] + ", " + __instance.ascLevelInfoString, true);
                    Output.setupUIBufferMany(TEXT[8], TEXT[9]);
                } else {
                    Output.textLocalized("ui.screens.CharacterSelectScreen", false, "label", TEXT[8]);
                }
            } else if (ascLeftHb.clicked || CInputActionSet.pageLeftViewDeck.isJustPressed() || ascRightHb.clicked
                    || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                if (__instance.isAscensionMode) {
                    Output.text(__instance.ascLevelInfoString, true);
                }
            }
        }
    }
}
