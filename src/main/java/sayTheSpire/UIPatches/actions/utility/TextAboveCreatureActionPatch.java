import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import sayTheSpire.Output;
import sayTheSpire.events.TextAboveCreatureEvent;

public class TextAboveCreatureActionPatch {

  @SpirePatch(
      clz = TextAboveCreatureAction.class,
      method = SpirePatch.CONSTRUCTOR,
      paramtypez = {AbstractCreature.class, TextAboveCreatureAction.TextType.class})
  public static class ConstructorWithoutSpecificMessagePatch {

    public static void Postfix(
        TextAboveCreatureAction __instance,
        AbstractCreature source,
        TextAboveCreatureAction.TextType type) {
      String msg =
          (String) ReflectionHacks.getPrivate(__instance, TextAboveCreatureAction.class, "msg");
      Output.event(new TextAboveCreatureEvent(source, msg));
    }
  }

  @SpirePatch(
      clz = TextAboveCreatureAction.class,
      method = SpirePatch.CONSTRUCTOR,
      paramtypez = {AbstractCreature.class, String.class})
  public static class ConstructorWithSpecificMessagePatch {

    public static void Postfix(
        TextAboveCreatureAction __instance, AbstractCreature source, String text) {
      Output.event(new TextAboveCreatureEvent(source, text));
    }
  }
}
