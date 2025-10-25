package sayTheSpire.utils;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.Output;

public class MonsterUtils {

    public static String getName(AbstractMonster monster) {
        String substitution = Output.localization.localize("substitutions.monster names." + monster.id);
        return substitution != null ? substitution : monster.name;
    }

}
