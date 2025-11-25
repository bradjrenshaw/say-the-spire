import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.RewardItemElement;

@SpirePatch(clz = RewardItem.class, method = "update")
public class RewardItemPatch {

    public static void Postfix(RewardItem __instance) {
        if (__instance.hb.justHovered) {
            Output.setUI(new RewardItemElement(__instance));
        }
    }
}
