package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import sayTheSpire.Output;
import sayTheSpire.utils.OutputUtils;

public class GainBlockEvent extends Event {

    private AbstractCreature creature;
    private int amount;

    public GainBlockEvent(AbstractCreature creature, int amount) {
        this.creature = creature;
        this.amount = amount;
    }

    public String getText() {
        return OutputUtils.getCreatureName(this.creature) + " +" + this.amount + " block";
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.block_text");
    }
}
