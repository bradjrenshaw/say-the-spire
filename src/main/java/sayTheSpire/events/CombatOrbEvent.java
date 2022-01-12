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
        super();
        this.orb = orb;
        this.slot = slot;
        this.action = action;
    }

    public String getText() {
        switch (this.action) {
        case CHANNEL:
            return this.orb.name + " channeled into slot " + this.slot;
        case EVOKE:
            return this.orb.name + " in slot " + this.slot + " evoked";
        default:
            return "Unknown action for " + this.orb.name;
        }
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.orb_events", true);
    }
}
