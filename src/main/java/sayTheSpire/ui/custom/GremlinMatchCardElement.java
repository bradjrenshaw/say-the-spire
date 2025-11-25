package sayTheSpire.ui.custom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.Position;

public class GremlinMatchCardElement extends CardElement {

    private Boolean isFlipped;

    public GremlinMatchCardElement(AbstractCard card, Position position) {
        super(card, CardElement.CardLocation.OTHER);
        this.position = position;
        this.isFlipped = true; // We don't want it reading the cards as they're initially flipped face down
        this.localization.put("target", this.card.name);
    }

    public void handleFlip() {
        if (card.isFlipped != this.isFlipped) {
            if (!card.isFlipped) {
                Output.text(this.localization.localize(".ui.elements.gremlin match card.revealed card"), false);
            } else {
                Output.text(this.localization.localize(".ui.elements.gremlin match card.flipped face down"), false);
            }
            this.isFlipped = this.card.isFlipped;
        }
    }

}
