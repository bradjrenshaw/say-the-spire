package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.utils.MonsterUtils;

public class MonsterElement extends UIElement {

    private AbstractMonster monster;

    public MonsterElement(AbstractMonster monster) {
        this(monster, null);
    }

    public MonsterElement(AbstractMonster monster, Position position) {
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

    public Position getPosition() {
        MonsterGroup group = AbstractDungeon.getCurrRoom().monsters;
        if (group == null)
            return null;
        int index = group.monsters.indexOf(this.monster);
        if (index < 0)
            return null;
        return new ListPosition(index, group.monsters.size());
    }
}
