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
