import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.ui.DialogWord;
import sayTheSpire.Output;

@SpirePatch(
    clz = RoomEventDialog.class,
    method = "updateBodyText",
    paramtypez = {String.class, DialogWord.AppearEffect.class})
public class RoomEventDialogPatch {

  public static void Prefix(RoomEventDialog __instance, String text, DialogWord.AppearEffect ae) {
    if (!text.equals("")) Output.eventText = text;
  }
}
