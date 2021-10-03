package sayTheSpire.ui;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;

public class PotionElement extends GameObjectElement {

    public enum PotionLocation {
        MAIN_SCREEN, COMBAT_REWARDS, COMPENDIUM, SHOP, OTHER
    };

    protected AbstractPotion potion;
    protected PotionLocation location;

    public PotionElement(AbstractPotion potion, PotionLocation location) {
        this(potion, location, null);
    }

    public PotionElement(AbstractPotion potion, PotionLocation location, AbstractPosition position) {
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

    public AbstractPosition getPosition() {
        switch (this.location) {
        case MAIN_SCREEN:
            return this.getInventoryPosition();
        default:
            return super.getPosition();
        }
    }

    public AbstractPosition getInventoryPosition() {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null)
            return null;
        int index = player.potions.indexOf(this.potion);
        if (index < 0 || index >= player.potionSlots)
            return null;
        return new ListPosition(index, player.potionSlots);
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupBuffers(potion);
        return null;
    }
}
