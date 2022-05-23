import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import sayTheSpire.ui.elements.MonsterElement;
import sayTheSpire.utils.MonsterUtils;
import sayTheSpire.Output;

@SpirePatch(clz = MonsterGroup.class, method = "update")
public class MonsterGroupPatch {

    public static AbstractMonster prevHoveredMonster = null;

    public static AbstractMonster getHoveredMonster(MonsterGroup group) {
        for (AbstractMonster monster : group.monsters) {
            if (MonsterUtils.getMonsterIsInCombat(monster) && monster.hb.hovered || monster.intentHb.hovered
                    || monster.healthHb.hovered) {
                return monster;
            }
        }
        return null;
    }

    public static void Prefix(MonsterGroup __instance) {
        AbstractMonster current = getHoveredMonster(__instance);
        if (current != prevHoveredMonster) {
            if (current != null) {
                Output.setUI(new MonsterElement(current));
            }
            prevHoveredMonster = current;
        }
    }
}
