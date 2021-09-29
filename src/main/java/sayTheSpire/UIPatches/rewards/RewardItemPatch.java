import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.ui.PotionElement;
import sayTheSpire.ui.RelicElement;
import sayTheSpire.Output;

@SpirePatch(clz = RewardItem.class, method = "update")
public class RewardItemPatch {

    public static void Postfix(RewardItem __instance) {
        if (__instance.hb.justHovered) {
            Output.text(__instance.text, true);
            switch (__instance.type) {
            case POTION:
                if (__instance.potion != null)
                    Output.setUI(new PotionElement(__instance.potion, PotionElement.PotionLocation.COMBAT_REWARDS));
                break;
            case RELIC:
                if (__instance.relic != null)
                    Output.setUI(new RelicElement(__instance.relic, RelicElement.RelicLocation.COMBAT_REWARDS));
                break;
            default:
                break;
            }
        }
    }
}
