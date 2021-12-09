package sayTheSpire.ui.custom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import basemod.ReflectionHacks;
import sayTheSpire.Output;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.elements.CardElement;

public class GremlinMatchCardElement extends CardElement {

    private Boolean isFlipped;

    public GremlinMatchCardElement(AbstractCard card, AbstractPosition position) {
        super(card, CardElement.CardLocation.OTHER);
        this.position = position;
        this.isFlipped = true; // We don't want it reading the cards as they're initially flipped face down
    }

    public void handleFlip() {
        if (card.isFlipped != this.isFlipped) {
            if (!card.isFlipped) {
                Output.text("Flippd card is " + this.card.name, false);
            } else {
                Output.text(this.card.name + " is flipped face down", false);
            }
            this.isFlipped = this.card.isFlipped;
        }
    }

}
