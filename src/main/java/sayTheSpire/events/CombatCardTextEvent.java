package sayTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sayTheSpire.Output;

public class CombatCardTextEvent extends Event {

    public enum EffectType {
        ADDED_TO_DISCARD, ADDED_TO_DRAW, ADDED_TO_HAND, DISCARD_SHUFFLED_INTO_DRAW, EXHAUSTED
    };

    private AbstractCard card;
    private EffectType type;

    public CombatCardTextEvent(EffectType type) {
        this(type, null);
    }

    public CombatCardTextEvent(EffectType type, AbstractCard card) {
        super("combatCardText");
        this.type = type;
        this.card = card;
        if (card != null)
            this.context.put("target", card.name);
    }

    public String getText() {
        switch (this.type) {
        case ADDED_TO_DISCARD:
            return this.context.localize("addedToDiscard");
        case ADDED_TO_DRAW:
            return this.context.localize("addedToDraw");
        case ADDED_TO_HAND:
            return this.context.localize("addedToHand");
        case DISCARD_SHUFFLED_INTO_DRAW:
            return this.context.localize("discardShuffledIntoDraw");
        case EXHAUSTED:
            return this.context.localize("exhausted");
        default:
            return null;
        }
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.card_events", true);
    }
}
