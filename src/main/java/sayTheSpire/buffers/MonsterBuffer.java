package sayTheSpire.buffers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.TextParser;

public class MonsterBuffer extends Buffer {

    private AbstractMonster monster;

    public MonsterBuffer(String name) {
        super("monster", name);
        this.monster = null;
    }

    public Object getObject() {
        return this.monster;
    }

    public void setObject(Object object) {
        this.monster = (AbstractMonster) object;
    }

    public void update() {
        this.clear();
        if (this.monster == null) {
            this.add("No monster available.");
            return;
        }
        AbstractMonster monster = this.monster;
        this.add(monster.name);
        this.add(monster.currentHealth + "/" + monster.maxHealth + " hp");
        this.add(monster.currentBlock + " block");
        this.add(OutputUtils.getCreaturePowersString(monster));
        for (PowerTip tip : (ArrayList<PowerTip>) ReflectionHacks.getPrivate(monster, AbstractCreature.class, "tips")) {
            this.add(TextParser.parse(tip.header + " NL " + tip.body));
        }
    }
}
