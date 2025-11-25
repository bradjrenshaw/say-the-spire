import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.MonsterElement;

@SpirePatch(clz = MonsterGroup.class, method = "update")
public class MonsterGroupPatch {

    public static MonsterElement prevHoveredMonster = null;

    public static MonsterElement getHoveredMonster(MonsterGroup group) {
        for (AbstractMonster m : group.monsters) {
            MonsterElement monster;
            if (prevHoveredMonster != null && prevHoveredMonster.getMonster() == m)
                monster = prevHoveredMonster;
            else
                monster = new MonsterElement(m);
            if (monster.isInCombat() && m.hb.hovered || m.intentHb.hovered || m.healthHb.hovered) {
                return monster;
            }
        }
        return null;
    }

    public static void Prefix(MonsterGroup __instance) {
        MonsterElement current = getHoveredMonster(__instance);
        if (current == null) {
            prevHoveredMonster = null;
            return;
        }
        if (prevHoveredMonster == null || !current.equals(prevHoveredMonster)) {
            Output.setUI(current);
            prevHoveredMonster = current;
        }
    }
}
