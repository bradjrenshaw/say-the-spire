import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

@SpirePatch(clz = TalkAction.class, method = "update")
public class TalkActionPatch {

    public static void Prefix(TalkAction __instance) {
        if (!(Boolean) ReflectionHacks.getPrivate(__instance, TalkAction.class, "used")) {
            String msg = (String) ReflectionHacks.getPrivate(__instance, TalkAction.class, "msg");
            Output.event(new DialogueEvent("says", __instance.source.name, TextParser.parse(msg)));
        }
    }
}
