import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.RenamePopup;
import sayTheSpire.Output;
import sayTheSpire.ui.mod.KeyboardContext;

public class RenamePopupPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RenamePanel");

    public static final String[] TEXT = uiStrings.TEXT;

    @SpirePatch(clz = RenamePopup.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(RenamePopup __instance) {
            Output.uiManager.pushContext(new KeyboardContext());
            Output.text(TEXT[1] + "\n" + Output.localization.localize("text.accessibility notes.keyboard"), true);
            Output.setupUIBufferMany(TEXT[1], Output.localization.localize("text.accessibility notes.keyboard"));
        }
    }

    @SpirePatch(clz = RenamePopup.class, method = "cancel")
    public static class CancelPatch {

        public static void Prefix(RenamePopup __instance) {
            Boolean shown = (Boolean) ReflectionHacks.getPrivate(__instance, RenamePopup.class, "shown");
            if (shown) {
                Output.uiManager.popContext();
            }
        }
    }

    @SpirePatch(clz = RenamePopup.class, method = "confirm")
    public static class ConfirmPatch {

        public static void Postfix(RenamePopup __instance) {
            Output.uiManager.popContext();
        }
    }
}
