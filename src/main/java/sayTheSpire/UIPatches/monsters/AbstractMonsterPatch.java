import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.events.CombatMonsterEvent;
import sayTheSpire.Output;

public class AbstractMonsterPatch {

    @SpirePatch(clz = AbstractMonster.class, method = "die", paramtypez = { boolean.class })
    public static class DiePatch {

        public static void Prefix(AbstractMonster __instance, boolean triggerRelics) {
            Output.event(new CombatMonsterEvent(__instance, CombatMonsterEvent.EffectType.DEFEATED));
        }
    }
}
