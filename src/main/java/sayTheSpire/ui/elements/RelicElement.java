package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.utils.RelicUtils;
import sayTheSpire.utils.OutputUtils;
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
        buffers.getBuffer("relic").setObject(relic);
        buffers.enableBuffer("relic", true);
        return "relic";
    }

    public String getExtrasString() {
        if (this.location == RelicLocation.SHOP) {
            return this.getPriceString();
        }
        return null;
    }

    public String getLabel() {
        return RelicUtils.getRelicShort(this.relic);
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
        int relicCount = player.relics.size();
        for (int r = 0; r < relicCount; r++) {
            if (this.relic == player.relics.get(r)) {
                return new ListPosition(r, relicCount);
            }
        }
        return null;
    }
}
