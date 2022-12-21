package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import sayTheSpire.ui.positions.*;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;

public class CardElement extends GameObjectElement {

    public enum CardLocation {
        CARD_REWARD, HAND, GRID_SELECT, HAND_SELECT, MASTER_DECK_VIEW, EXHAUST_PILE_VIEW, DISCARD_PILE_VIEW,
        DRAW_PILE_VIEW, SHOP, COMPENDIUM, OTHER
    }

    protected AbstractCard card;
    private CardLocation location;

    public CardElement(AbstractCard card) {
        this(card, CardLocation.OTHER);
    }

    public CardElement(AbstractCard card, CardLocation location) {
        this(card, location, null);
    }

    public CardElement(AbstractCard card, CardLocation location, Position position) {
        super("card", position);
        this.card = card;
        this.location = location;
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("current card").setObject(card);
        buffers.getBuffer("upgrade preview").setObject(card);
        buffers.enableBuffer("current card", true);
        buffers.enableBuffer("upgrade preview", true);
        return "current card";
    }

    public String getExtrasString() {
        if (this.card.isFlipped || this.card.isLocked)
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append(CardUtils.getCardCostString(this.card));
        if (this.location == CardLocation.SHOP)
            sb.append(", " + this.getPriceString());
        return sb.toString();
    }

    public String getLabel() {
        if (this.card.isFlipped)
            return this.localization.localize("face down");
        else if (this.card.isLocked)
            return this.card.LOCKED_STRING;
        return this.card.name;
    }

    public ListPosition getListPosition(ArrayList<AbstractCard> list) {
        if (list == null)
            return null;
        int index = list.indexOf(this.card);
        if (index < 0)
            return null;
        return new ListPosition(index, list.size());
    }

    public Position getPosition() {
        int cardsPerLine = 5; // default
        switch (this.location) {
        case HAND:
            return this.getListPosition(OutputUtils.getPlayer().hand.group);
        case GRID_SELECT:
            ArrayList<AbstractCard> grid = AbstractDungeon.gridSelectScreen.targetGroup.group;
            return CardUtils.getGridPosition(this.card, grid, cardsPerLine);
        case HAND_SELECT:
            HandCardSelectScreen screen = AbstractDungeon.handCardSelectScreen;
            if (screen == null)
                return null;
            CardGroup unselectedGroup = (CardGroup) ReflectionHacks.getPrivate(screen, HandCardSelectScreen.class,
                    "hand");
            ArrayList<AbstractCard> unselected = unselectedGroup.group;
            ArrayList<AbstractCard> selected = screen.selectedCards.group;
            Position result = this.getListPosition(selected);
            if (result == null)
                result = this.getListPosition(unselected);
            return result;
        default:
            return super.getPosition();
        }
    }

}
