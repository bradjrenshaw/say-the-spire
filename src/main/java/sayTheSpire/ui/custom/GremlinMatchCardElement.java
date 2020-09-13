package sayTheSpire.ui.custom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sayTheSpire.ui.CardElement;
import sayTheSpire.Output;

public class GremlinMatchCardElement extends CardElement {


    private Boolean isFlipped;
    private int x;
    private int y;

    public GremlinMatchCardElement(AbstractCard card, int x, int y) {
        super(card, CardElement.LocationType.OTHER);
        this.x = x;
        this.y = y;
        this.isFlipped = card.isFlipped;
    }

    public void update() {
        if (card.isFlipped != this.isFlipped) {
            if (!card.isFlipped) {
                Output.text("Flippd card is " + this.card.name, false);
            } else {
                Output.text(this.card.name + " is flipped face down", false);
            }
        }
        this.isFlipped = this.card.isFlipped;
    }

    public String getPositionString() {
        return x + ", " + y;
    }
}
