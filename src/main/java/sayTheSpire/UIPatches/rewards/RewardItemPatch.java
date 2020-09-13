import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sayTheSpire.Output;

@SpirePatch(clz = RewardItem.class, method = "update")
public class RewardItemPatch {

  public static void Postfix(RewardItem __instance) {
    if (__instance.hb.justHovered) {
      Output.text(__instance.text, true);
      switch (__instance.type) {
        case POTION:
          Output.setupBuffers(__instance.potion);
          break;
        case RELIC:
          Output.setupBuffers(__instance.relic);
          break;
        default:
          break;
      }
    }
  }
}
