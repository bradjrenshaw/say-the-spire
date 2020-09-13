import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.DeleteSaveConfirmPopup;
import sayTheSpire.Output;

public class DeleteSaveConfirmPopupPatch {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DeletePopup");

  public static final String[] TEXT = uiStrings.TEXT;

  @SpirePatch(clz = DeleteSaveConfirmPopup.class, method = "open")
  public static class OpenPatch {

    public static void Postfix(DeleteSaveConfirmPopup __instance) {
      Output.text(TEXT[0] + "\n" + TEXT[3], true);
      Output.setupUIBufferMany(TEXT[0], TEXT[3]);
    }
  }
}
