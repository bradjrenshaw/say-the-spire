package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.Output;
import sayTheSpire.utils.OutputUtils;

public class GainBlockEvent extends Event {

    private AbstractCreature creature;
    private int amount;

    public GainBlockEvent(AbstractCreature creature, int amount) {
        super("gainBlock");
        this.creature = creature;
        this.amount = amount;
        this.context.put("target", OutputUtils.getCreatureName(creature));
        this.context.put("amount", this.amount);
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.block_text");
    }
}
