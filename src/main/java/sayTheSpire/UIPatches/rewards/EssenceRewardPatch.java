import basemod.abstracts.CustomReward;
import collector.util.EssenceReward;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.RewardItemElement;

@SpirePatch(clz = CustomReward.class, method = "update")
public class EssenceRewardPatch {

    public static void Postfix(CustomReward __instance) {
        if (__instance instanceof EssenceReward && __instance.hb.justHovered) {
            Output.setUI(new RewardItemElement((RewardItem) __instance));
        }
    }
}
