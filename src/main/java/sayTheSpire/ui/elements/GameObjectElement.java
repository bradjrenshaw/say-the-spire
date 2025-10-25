package sayTheSpire.ui.elements;

import sayTheSpire.ui.positions.Position;

public abstract class GameObjectElement extends UIElement {

    private int price;
    private Boolean onSale;

    public GameObjectElement(String elementType, Position position) {
        super(elementType, position);
        this.price = -1;
        this.onSale = false;
    }

    public String getPriceString() {
        if (this.onSale) {
            return this.localization.localize(".ui.elements.shop item.priceSaleString");
        }
        return this.localization.localize(".ui.elements.shop item.priceString");
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price, Boolean onSale) {
        this.price = price;
        this.onSale = onSale;
        this.localization.put("price", price);
    }
}
