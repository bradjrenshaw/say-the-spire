import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.ui.mod.KeyboardContext;

public class SeedPanelPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SeedPanel");

    public static final String[] TEXT = uiStrings.TEXT;

    @SpirePatch(clz = SeedPanel.class, method = "close")
    public static class ClosePatch {

        public static void Prefix(SeedPanel __instance) {
            Output.uiManager.popContext();
        }
    }

    @SpirePatch(clz = SeedPanel.class, method = "show", paramtypez = {})
    public static class ShowPatch {

        public static void Prefix(SeedPanel __instance) {
            Output.uiManager.pushContext(new KeyboardContext());
            Output.text(TEXT[1] + "\nAccessibility note: Use keyboard to enter seed.", false);
            Output.setupUIBufferMany(TEXT[1], "Accessibility note: Use keyboard to enter seed.",
                    TextParser.parse(TEXT[4]), TextParser.parse(TEXT[5]));
        }
    }
}
