package sayTheSpire.buffers;

import com.megacrit.cardcrawl.helpers.PowerTip;
import sayTheSpire.TextParser;
import sayTheSpire.ui.elements.MonsterElement;

public class MonsterBuffer extends Buffer {

    private MonsterElement monster;

    public MonsterBuffer(String name) {
        super("monster", name);
        this.monster = null;
    }

    public Object getObject() {
        return this.monster;
    }

    public void setObject(Object object) {
        this.monster = (MonsterElement) object;
    }

    public void update() {
        this.clear();
        if (this.monster == null) {
            this.addLocalized("noObj");
            return;
        }
        this.context.put("hp", this.monster.getCurrentHealth());
        this.context.put("hpMax", this.monster.getMaxHealth());
        this.context.put("block", this.monster.getCurrentBlock());
        this.add(this.monster.getName());
        this.addLocalized("content.hp");
        this.addLocalized("content.block");
        this.add(this.monster.getPowersString());
        for (PowerTip tip : monster.getTips()) {
            this.add(TextParser.parse(tip.header + " NL " + tip.body));
        }
    }
}
