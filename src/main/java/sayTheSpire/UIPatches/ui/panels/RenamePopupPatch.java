import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.RenamePopup;
import sayTheSpire.Output;

public class RenamePopupPatch {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RenamePanel");

  public static final String[] TEXT = uiStrings.TEXT;

  @SpirePatch(clz = RenamePopup.class, method = "open")
  public static class OpenPatch {

    public static void Postfix(RenamePopup __instance) {
      Output.text(TEXT[1] + "\nAccessibility note: Use keyboard to enter name.", true);
      Output.setupUIBufferMany(TEXT[1], "Accessibility note: Use keyboard to enter name.");
    }
  }
}
