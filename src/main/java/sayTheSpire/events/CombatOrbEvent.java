package sayTheSpire.events;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sayTheSpire.Output;

public class CombatOrbEvent extends Event {

    public enum ACTION {
        CHANNEL, EVOKE, OTHER
    };

    private ACTION action;
    private AbstractOrb orb;
    private int slot;

    public CombatOrbEvent(AbstractOrb orb, int slot, ACTION action) {
        super("combatOrb");
        this.orb = orb;
        this.slot = slot;
        this.action = action;
        this.context.put("target", this.orb.name);
        this.context.put("slot", this.slot);
    }

    public String getText() {
        switch (this.action) {
        case CHANNEL:
            return this.context.localize("channel");
        case EVOKE:
            return this.context.localize("evoke");
        default:
            return this.context.localize("unknown");
        }
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.orb_events");
    }
}
