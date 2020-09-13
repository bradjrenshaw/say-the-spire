import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.FtueTip;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class FtueTipPatch {

  @SpirePatch(
      clz = FtueTip.class,
      method = "openScreen",
      paramtypez = {String.class, String.class, float.class, float.class})
  public static class OpenScreenPatch {

    public static void Prefix(FtueTip __instance, String header, String body, float x, float y) {
      Output.text(header + "\n" + TextParser.parse(body), true);
      Output.setupUIBufferMany(header, TextParser.parse(body));
    }
  }
}
