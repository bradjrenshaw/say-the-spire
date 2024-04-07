package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.utils.OutputUtils;

public class PotionElement extends GameObjectElement {

    public enum PotionLocation {
        MAIN_SCREEN, COMBAT_REWARDS, COMPENDIUM, SHOP, OTHER
    };

    protected AbstractPotion potion;
    protected PotionLocation location;

    public PotionElement(AbstractPotion potion, PotionLocation location) {
        this(potion, location, null);
    }

    public PotionElement(AbstractPotion potion, PotionLocation location, Position position) {
        super("potion", position);
        this.potion = potion;
        this.location = location;
    }

    public String getExtrasString() {
        if (this.location == PotionLocation.SHOP) {
            return this.getPriceString();
        }
        return null;
    }

    public String getLabel() {
        return this.potion.name;
    }

    public Position getPosition() {
        switch (this.location) {
        case MAIN_SCREEN:
            return this.getInventoryPosition();
        default:
            return super.getPosition();
        }
    }

    public Position getInventoryPosition() {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null)
            return null;
        int index = player.potions.indexOf(this.potion);
        if (index < 0 || index >= player.potionSlots)
            return null;
        return new ListPosition(index, player.potionSlots);
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("potion").setObject(potion);
        buffers.enableBuffer("potion", true);
        return "potion";
    }
}
