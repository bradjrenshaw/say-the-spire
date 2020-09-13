import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

@SpirePatch(clz = ShoutAction.class, method = "update")
public class ShoutActionPatch {

  public static void Prefix(ShoutAction __instance) {
    if (!(Boolean) ReflectionHacks.getPrivate(__instance, ShoutAction.class, "used")) {
      String msg = (String) ReflectionHacks.getPrivate(__instance, ShoutAction.class, "msg");
      Output.event(new DialogueEvent("shouts", __instance.source.name, TextParser.parse(msg)));
    }
  }
}
