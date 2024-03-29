import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.events.CombatCardTextEvent;
import sayTheSpire.Output;

public class EmptyDeckShuffleActionPatch {

    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class UpdatePatch {

        public static void Prefix(EmptyDeckShuffleAction __instance) {
            boolean shuffled = (boolean) ReflectionHacks.getPrivate(__instance, EmptyDeckShuffleAction.class,
                    "shuffled");
            if (!shuffled) {
                Output.event(new CombatCardTextEvent(CombatCardTextEvent.EffectType.DISCARD_SHUFFLED_INTO_DRAW));
            }
        }
    }
}
