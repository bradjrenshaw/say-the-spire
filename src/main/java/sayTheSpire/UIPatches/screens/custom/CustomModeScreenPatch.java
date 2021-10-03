import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.ui.elements.ToggleButtonElement;

public class CustomModeScreenPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CustomModeScreen");

    public static final String[] TEXT = uiStrings.TEXT;
    public static ToggleButtonElement currentButton = null;

    public static int lastAscensionLevel = -1;
    public static Boolean lastIsAscensionMode = false;

    public static String getAscensionLevelString(int level) {
        if (level <= 0)
            return CharacterSelectScreen.A_TEXT[0];
        return CharacterSelectScreen.A_TEXT[level - 1];
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(CustomModeScreen __instance) {
            lastAscensionLevel = __instance.ascensionLevel;
            lastIsAscensionMode = __instance.isAscensionMode;
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "updateAscension")
    public static class updateAscensionPatch {

        public static void Postfix(CustomModeScreen __instance) {
            Hitbox ascensionModeHb = (Hitbox) ReflectionHacks.getPrivate(__instance, CustomModeScreen.class,
                    "ascensionModeHb");
            int currentAscensionLevel = __instance.ascensionLevel;
            Boolean currentIsAscensionMode = __instance.isAscensionMode;
            if (ascensionModeHb.justHovered) {
                currentButton = new ToggleButtonElement(TEXT[5], __instance.isAscensionMode);
                Output.setUI(currentButton);
            } else if (currentIsAscensionMode != lastIsAscensionMode) {
                if (currentIsAscensionMode) {
                    Output.text(TEXT[5] + " " + getAscensionLevelString(currentAscensionLevel), true);
                } else {
                    Output.text(TEXT[5] + " off", true);
                }
            } else if (currentAscensionLevel != lastAscensionLevel) {
                Output.text(getAscensionLevelString(currentAscensionLevel), true);
            }
            lastAscensionLevel = currentAscensionLevel;
            lastIsAscensionMode = currentIsAscensionMode;
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "updateSeed")
    public static class UpdateSeedPatch {

        public static void Postfix(CustomModeScreen __instance) {
            Hitbox seedHb = (Hitbox) ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "seedHb");
            if (seedHb.justHovered) {
                String currentSeed = (String) ReflectionHacks.getPrivate(__instance, CustomModeScreen.class,
                        "currentSeed");
                ButtonElement button = new ButtonElement(TEXT[8] + " (" + currentSeed + ")", TEXT[8],
                        "Accessibility note: Current seed is " + currentSeed);
                Output.setUI(button);
            }
        }
    }
}
