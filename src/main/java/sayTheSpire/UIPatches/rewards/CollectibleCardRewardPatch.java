import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.RewardItemElement;

@SpirePatch(cls = "collector.util.CollectibleCardReward", method = "update", optional = true)
public class CollectibleCardRewardPatch {

    public static void Postfix(Object __instance) {
        if (((RewardItem) __instance).hb.justHovered) {
            Output.setUI(new RewardItemElement((RewardItem) __instance));
        }
    }
}
