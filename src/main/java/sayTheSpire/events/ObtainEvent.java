package sayTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sayTheSpire.Output;

public class ObtainEvent extends Event {

    public enum ObtainType {
        CARD_ADDED_TO_DECK, GOLD, OBJECT
    };

    private ObtainType obtainType;
    private AbstractCard card;
    private String target;
    private int gold;

    public ObtainEvent(AbstractCard card) {
        super("obtain");
        this.obtainType = ObtainType.CARD_ADDED_TO_DECK;
        this.card = card;
        this.context.put("target", card.name);
    }

    public ObtainEvent(int amount) {
        super("obtain");
        this.obtainType = ObtainType.GOLD;
        this.gold = amount;
        this.context.put("amount", amount);
    }

    public ObtainEvent(String target) {
        super("obtain");
        this.target = target;
        this.obtainType = ObtainType.OBJECT;
        this.context.put("target", target);
    }

    public String getText() {
        switch (this.obtainType) {
        case CARD_ADDED_TO_DECK:
            return this.context.localize("cardAddedToDeck");
        case GOLD:
            return this.context.localize("gold");
        case OBJECT:
            return this.context.localize("object");
        }
        return null;
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("ui.read_obtain_events");
    }
}
