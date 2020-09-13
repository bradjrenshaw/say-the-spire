import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class SeedPanelPatch {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SeedPanel");

  public static final String[] TEXT = uiStrings.TEXT;

  @SpirePatch(
      clz = SeedPanel.class,
      method = "show",
      paramtypez = {})
  public static class ShowPatch {

    public static void Postfix(SeedPanel __instance) {
      Output.text(TEXT[1] + "\nAccessibility note: Use keyboard to enter seed.", true);
      Output.setupUIBufferMany(
          TEXT[1],
          "Accessibility note: Use keyboard to enter seed.",
          TextParser.parse(TEXT[4]),
          TextParser.parse(TEXT[5]));
    }
  }
}
