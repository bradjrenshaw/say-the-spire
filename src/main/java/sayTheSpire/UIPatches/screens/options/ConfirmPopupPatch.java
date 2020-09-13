import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class ConfirmPopupPatch {

  @SpirePatch(clz = ConfirmPopup.class, method = "show")
  public static class ShowPatch {

    public static void Postfix(ConfirmPopup __instance) {
      Output.text(__instance.title + "\n" + TextParser.parse(__instance.desc), true);
      Output.setupUIBufferMany(__instance.title, TextParser.parse(__instance.desc));
    }
  }
}
