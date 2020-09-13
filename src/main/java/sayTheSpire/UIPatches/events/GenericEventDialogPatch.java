import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.ui.DialogWord;
import sayTheSpire.Output;

public class GenericEventDialogPatch {

  @SpirePatch(
      clz = GenericEventDialog.class,
      method = "updateBodyText",
      paramtypez = {String.class, DialogWord.AppearEffect.class})
  public static class updateBodyTextPatch {
    public static void Prefix(
        GenericEventDialog __instance, String text, DialogWord.AppearEffect ae) {
      if (!text.equals("")) Output.eventText = text;
    }
  }
}
