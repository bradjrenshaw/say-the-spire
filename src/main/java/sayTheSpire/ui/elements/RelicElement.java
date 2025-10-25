package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.utils.OutputUtils;

public class RelicElement extends GameObjectElement {

    public enum RelicLocation {
        MAIN_SCREEN, COMPENDIUM, BOSS_REWARDS, COMBAT_REWARDS, SHOP, OTHER
    }

    private AbstractRelic relic;
    private RelicLocation location;

    public RelicElement(AbstractRelic relic, RelicLocation location) {
        this(relic, location, null);
    }

    public RelicElement(AbstractRelic relic, RelicLocation location, Position position) {
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
        String extras = "";
        if (this.relic.counter >= 0) {
            this.localization.put("counter", this.relic.counter);
            extras = this.localization.localize("counterString");
        }
        if (this.location == RelicLocation.SHOP) {
            extras += " " + this.getPriceString();
            return extras;
        }
        if (extras.isEmpty())
            return null;
        return extras;
    }

    public String getLabel() {
        return this.relic.name;
    }

    public Position getPosition() {
        switch (this.location) {
        case MAIN_SCREEN:
            return this.getInventoryPosition();
        default:
            return super.getPosition();
        }
    }

    /*
     * public AbstractPosition getBossRewardsPosition() { if (AbstractDungeon.screen !=
     * AbstractDungeon.CurrentScreen.BOSS_REWARD) return null; AbstractRoom currentRoom = AbstractDungeon.getCurrRoom();
     * if (currentRoom == null) return null; if (!(currentRoom instanceof TreasureRoomBoss)) return null;
     * TreasureRoomBoss bossRoom = (TreasureRoomBoss)currentRoom; if (bossRoom.chest == null || !(bossRoom.chest
     * instanceof BossChest)) return null; if (bossRoom.chest.relics == null) return null; int relicCount =
     * bossRoom.chest.relics.size();
     * 
     * }
     */

    public Position getInventoryPosition() {
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
