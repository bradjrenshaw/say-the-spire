package sayTheSpire.ui.custom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sayTheSpire.Output;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.elements.CardElement;

public class GremlinMatchCardElement extends CardElement {

    private Boolean isFlipped;

    public GremlinMatchCardElement(AbstractCard card, AbstractPosition position) {
        super(card, CardElement.CardLocation.OTHER);
        this.position = position;
        this.isFlipped = card.isFlipped;
    }

    public void update() {
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
