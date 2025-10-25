package sayTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class MiscCardEvent extends MiscEvent {

    private AbstractCard card;

    public MiscCardEvent(String name, AbstractCard card) {
        super(name);
        this.card = card;
        this.context.put("target", this.card.name);
    }
}
