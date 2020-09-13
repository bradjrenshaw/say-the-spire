package sayTheSpire.ui;

import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import basemod.ReflectionHacks;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.BufferManager;
import sayTheSpire.Output;

public class CardElement extends UIElement {

    public enum LocationType {
        HAND,
        GRID_SELECT,
        HAND_SELECT,
        MASTER_DECK_VIEW,
        EXHAUST_PILE_VIEW,
        DISCARD_PILE_VIEW,
        DRAW_PILE_VIEW,
        SHOP,
        OTHER
    }
    
    protected AbstractCard card;
    private LocationType location;
private String priceString;

    public CardElement(AbstractCard card) {
        this(card, LocationType.OTHER);
    }

    public CardElement(AbstractCard card, LocationType location) {
        this.card = card;
        this.location = location;
        this.priceString = null; //Not in shop
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupBuffers(this.card);
        return null;
    }

        public String getExtrasString() {
            StringBuilder sb = new StringBuilder();
            sb.append(CardUtils.getCardCostString(this.card));
            if (this.priceString != null) sb.append(", " + this.priceString);
            return sb.toString();
        }

    public String getLabel() {
        return this.card.name;
    }

    public String getGridPositionString(ArrayList<AbstractCard> grid, int width) {
        if (grid == null) return null;
            int gridIndex = grid.indexOf(this.card);
            if (gridIndex < 0) return null;
            int row = gridIndex/width + 1;
            int column = gridIndex % width + 1;
            return column + ", " + row;
                }

                public String getListPositionString(ArrayList<AbstractCard> list) {
                    if (list == null) return null;
                    int index = list.indexOf(this.card);
                    if (index < 0) return null;
                    return (index + 1) + " of " + list.size();
                }

    public String getPositionString() {
        int cardsPerLine = 5; //default
        switch(this.location) {
            case HAND:
            return this.getListPositionString(OutputUtils.getPlayer().hand.group);
            case GRID_SELECT:
            ArrayList<AbstractCard> grid = AbstractDungeon.gridSelectScreen.targetGroup.group;
            return this.getGridPositionString(grid, cardsPerLine);
            case HAND_SELECT:
            HandCardSelectScreen screen = AbstractDungeon.handCardSelectScreen;
            if (screen == null) return null;
                        CardGroup unselectedGroup = (CardGroup)ReflectionHacks.getPrivate(screen, HandCardSelectScreen.class, "hand");
                        ArrayList<AbstractCard> unselected = unselectedGroup.group;
                        ArrayList<AbstractCard> selected = screen.selectedCards.group;
                        String result = this.getListPositionString(selected);
                        if (result == null)
                        result = getListPositionString(unselected);
                        return result;
                        case MASTER_DECK_VIEW:
                        MasterDeckViewScreen masterScreen = AbstractDungeon.deckViewScreen;
                                                ArrayList<AbstractCard> group = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate(masterScreen, MasterDeckViewScreen.class, "tmpSortedDeck");
                        if (group == null) {
                            group = AbstractDungeon.player.masterDeck.group;
                        }
                        return this.getGridPositionString(group, cardsPerLine);
                        case EXHAUST_PILE_VIEW:
                        CardGroup exhaustGroup = (CardGroup)ReflectionHacks.getPrivate(AbstractDungeon.exhaustPileViewScreen, ExhaustPileViewScreen.class, "exhaustPileCopy");
                        if (exhaustGroup == null) return null;
                        cardsPerLine = (int)ReflectionHacks.getPrivate(AbstractDungeon.exhaustPileViewScreen, ExhaustPileViewScreen.class, "CARDS_PER_LINE");

                        return this.getGridPositionString(exhaustGroup.group, cardsPerLine);
                        case DRAW_PILE_VIEW:
                        CardGroup drawGroup = (CardGroup)ReflectionHacks.getPrivate(AbstractDungeon.gameDeckViewScreen, DrawPileViewScreen.class, "drawPileCopy");                     
                        if (drawGroup == null) return null;
                        cardsPerLine = (int)ReflectionHacks.getPrivate(AbstractDungeon.gameDeckViewScreen, DrawPileViewScreen.class, "CARDS_PER_LINE");
                        return this.getListPositionString(drawGroup.group);
                        case DISCARD_PILE_VIEW:
                        CardGroup discardGroup = (CardGroup)ReflectionHacks.getPrivate(AbstractDungeon.discardPileViewScreen, DiscardPileViewScreen.class, "discardPileCopy");
                        if (discardGroup == null) return null;
                        cardsPerLine = (int)ReflectionHacks.getPrivate(AbstractDungeon.discardPileViewScreen, DiscardPileViewScreen.class, "CARDS_PER_LINE");                       
                        return this.getGridPositionString(discardGroup.group, cardsPerLine);
                                            default:
                        return null;
        }
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }
}
