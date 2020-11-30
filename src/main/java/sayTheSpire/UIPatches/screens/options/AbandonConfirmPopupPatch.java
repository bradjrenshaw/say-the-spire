/*
 * An update to Slay the Spire removed this class from the game. this file is kept here for now in case it is needed at some point.
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;
import sayTheSpire.Output;

public class AbandonConfirmPopupPatch {

  @SpirePatch(clz = AbandonConfirmPopup.class, method = "show")
  public static class ShowPatch {

    public static void Postfix(AbandonConfirmPopup __instance) {
      String header = AbandonConfirmPopup.TEXT_DESC[0];
      String body = AbandonConfirmPopup.TEXT_DESC[2];
      Output.text(header + "\n" + body, true);
      Output.setupUIBufferMany(header, body);
    }
  }
}
*/