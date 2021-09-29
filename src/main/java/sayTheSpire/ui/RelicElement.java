package sayTheSpire.ui;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.Output;

public class RelicElement extends GameObjectElement {

    public enum RelicLocation {
        MAIN_SCREEN, COMPENDIUM, COMBAT_REWARDS, SHOP, OTHER
    }

    private AbstractRelic relic;
    private RelicLocation location;

    public RelicElement(AbstractRelic relic, RelicLocation location) {
        this(relic, location, null);
    }

    public RelicElement(AbstractRelic relic, RelicLocation location, AbstractPosition position) {
        super("relic", position);
        this.relic = relic;
        this.location = location;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupBuffers(this.relic);
        return null;
    }

    public String getExtrasString() {
        if (this.location == RelicLocation.SHOP) {
            return this.getPriceString();
        }
        return null;
    }

    public String getLabel() {
        return this.relic.name;
    }

    public AbstractPosition getPosition() {
        switch (this.location) {
        default:
            return super.getPosition();
        }
    }
}
