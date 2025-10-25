import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.neow.NeowEvent;
import sayTheSpire.Output;

@SpirePatch(clz = NeowEvent.class, method = "talk", paramtypez = { String.class })
public class NeowEventPatch {

    public static void Prefix(NeowEvent __instance, String text) {
        Output.text(sayTheSpire.utils.EventUtils.parseBodyText(text), false);
    }
}
