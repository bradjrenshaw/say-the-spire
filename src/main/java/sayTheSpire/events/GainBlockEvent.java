package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import sayTheSpire.Output;
import sayTheSpire.utils.OutputUtils;

public class GainBlockEvent extends Event {

    private AbstractCreature creature;
    private int amount;

    public GainBlockEvent(AbstractCreature creature, int amount) {
        super("gainBlock");
        this.creature = creature;
        this.amount = amount;
        this.context.put("target", this.creature.name);
        this.context.put("amount", this.amount);
    }

    public String getText() {
        return this.context.localize("text");
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.block_text");
    }
}
