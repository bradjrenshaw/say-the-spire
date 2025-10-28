package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;

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
        RewardItem.RewardType collectorType = null;

        try {
            Class<?> patch = Class.forName("downfall.patches.RewardItemTypeEnumPatch");
            collectorType = ReflectionHacks.getPrivateStatic(patch, "COLLECTOR_COLLECTIBLECARDREWARD");
        } catch (Throwable ignored) {
            collectorType = null;
        }

        if (this.reward.type == RewardItem.RewardType.POTION) {
            this.rewardUIElement = new PotionElement(this.reward.potion, PotionElement.PotionLocation.COMBAT_REWARDS);
        } else if (this.reward.type == RewardItem.RewardType.RELIC) {
            this.rewardUIElement = new RelicElement(this.reward.relic, RelicElement.RelicLocation.COMBAT_REWARDS);
        } else if (this.reward.type == collectorType) {
            this.rewardUIElement = new CardElement(
                    (AbstractCard) ReflectionHacks.getPrivate(this.reward, this.reward.getClass(), "card"),
                    CardElement.CardLocation.CARD_REWARD, this.getPosition());
        } else {
            this.rewardUIElement = new ButtonElement(this.reward.text);
        }
    }

    public String getLabel() {
        return this.rewardUIElement.getLabel();
    }

    public RewardItem getRewardItem() {
        return reward;
    }

    public Position getPosition() {
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
