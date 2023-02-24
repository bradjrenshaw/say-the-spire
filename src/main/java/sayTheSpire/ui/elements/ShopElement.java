package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import basemod.ReflectionHacks;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.positions.GridPosition;

public class ShopElement extends UIElement {

    public enum ShopElementType {
        BUTTON, CARD, POTION, PURGE_BUTTON, RELIC
    }

    private ShopScreen screen;
    private ShopElementType elementType;
    private AbstractCard card;
    private StorePotion storePotion;
    private StoreRelic storeRelic;
    private UIElement itemElement;
    private GridPosition position;

    public ShopElement(ButtonElement element, ShopElementType elementType, GridPosition position) {
        super("button", position);
        this.elementType = elementType;
        this.itemElement = element;
        this.position = position;
    }

    public ShopElement(AbstractCard card, GridPosition position) {
        super("card", position);
        this.elementType = ShopElementType.CARD;
        this.card = card;
        this.itemElement = new CardElement(card, CardElement.CardLocation.SHOP);
        this.position = position;
    }

    public ShopElement(StorePotion storePotion, GridPosition position) {
        super("potion", position);
        this.elementType = ShopElementType.POTION;
        this.storePotion = storePotion;
        this.itemElement = new PotionElement(storePotion.potion, PotionElement.PotionLocation.SHOP);
        this.position = position;
    }

    public ShopElement(StoreRelic storeRelic, GridPosition position) {
        super("relic", position);
        this.elementType = ShopElementType.RELIC;
        this.storeRelic = storeRelic;
        this.itemElement = new RelicElement(storeRelic.relic, RelicElement.RelicLocation.SHOP);
        this.position = position;
    }

    public String handleBuffers(BufferManager buffers) {
        return this.itemElement.handleBuffers(buffers);
    }

    public int getPrice() {
        switch (this.elementType) {
        case CARD:
            return this.card.price;
        case POTION:
            return this.storePotion.price;
        case RELIC:
            return this.storeRelic.price;
        case PURGE_BUTTON:
            return AbstractDungeon.shopScreen.actualPurgeCost;
        default:
            return -1;
        }
    }

    public Boolean getOnSale() {
        OnSaleTag tag = (OnSaleTag) ReflectionHacks.getPrivate(AbstractDungeon.shopScreen, ShopScreen.class, "saleTag");

        if (this.elementType != ShopElementType.CARD)
            return false;
        return tag.card == this.card;
    }

    public String getExtrasString() {
        int price = this.getPrice();
        if (price < 0)
            return null;
        this.localization.put("price", price);
        if (this.getOnSale()) {
            return this.localization.localize(".ui.elements.shop item.priceSaleString");
        }
        return this.localization.localize(".ui.elements.shop item.priceString");
    }

    public String getLabel() {
        return this.itemElement.getLabel();
    }

    public Position getPosition() {
        return this.position;
    }
}
