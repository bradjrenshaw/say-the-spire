package sayTheSpire.ui;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.ui.positions.ListPosition;

public class RewardItemElement extends UIElement {

    private RewardItem reward;
    private UIElement rewardUIElement;

    public RewardItemElement(RewardItem reward) {
        super("button");
        this.reward = reward;
        this.setupUIElement();
    }

    public String handleBuffers(BufferManager buffers) {
        return this.rewardUIElement.handleBuffers(buffers);
    }

    private void setupUIElement() {
        switch (this.reward.type) {
        case POTION:
            this.rewardUIElement = new PotionElement(this.reward.potion, PotionElement.PotionLocation.COMBAT_REWARDS);
            break;
        case RELIC:
            this.rewardUIElement = new RelicElement(this.reward.relic, RelicElement.RelicLocation.COMBAT_REWARDS);
            break;
        default:
            this.rewardUIElement = new ButtonElement(this.reward.text);
        }
    }

    public String getLabel() {
        return this.rewardUIElement.getLabel();
    }

    public AbstractPosition getPosition() {
        if (AbstractDungeon.combatRewardScreen == null || AbstractDungeon.combatRewardScreen.rewards == null)
            return null;
        int rewardCount = AbstractDungeon.combatRewardScreen.rewards.size();
        for (int r = 0; r < rewardCount; r++) {
            RewardItem reward = AbstractDungeon.combatRewardScreen.rewards.get(r);
            if (reward == this.reward) {
                return new ListPosition(r, rewardCount);
            }
        }
        return null;
    }
}
