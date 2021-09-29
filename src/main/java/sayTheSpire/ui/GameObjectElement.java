package sayTheSpire.ui;

import sayTheSpire.ui.positions.AbstractPosition;

public abstract class GameObjectElement extends UIElement {

    private int price;
    private Boolean onSale;

    public GameObjectElement(String elementType, AbstractPosition position) {
        super(elementType, position);
        this.price = -1;
        this.onSale = false;
    }

    public String getPriceString() {
        String priceString = this.getPrice() + "";
        if (this.onSale)
            priceString += " (on sale)";
        return priceString;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price, Boolean onSale) {
        this.price = price;
        this.onSale = onSale;
    }
}
