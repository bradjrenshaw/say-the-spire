package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.utils.MonsterUtils;

public class MonsterElement extends UIElement {

    private AbstractMonster monster;

    public MonsterElement(AbstractMonster monster) {
        this(monster, null);
    }

    public MonsterElement(AbstractMonster monster, AbstractPosition position) {
        super("monster", position);
        this.monster = monster;
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("monster").setObject(this.monster);
        buffers.enableBuffer("monster", true);
        return "monster";
    }

    public String getLabel() {
        return MonsterUtils.getMonsterShort(this.monster);
    }

}
