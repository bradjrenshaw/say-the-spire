import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.Output;
import sayTheSpire.utils.RelicUtils;

@SpirePatch(clz = AbstractRelic.class, method = "update")
public class AbstractRelicPatch {

  public static void Prefix(AbstractRelic __instance) {
    if (__instance.hb.justHovered) {
      Output.text(RelicUtils.getRelicShort(__instance), true);
      Output.setupBuffers(__instance);
    }
  }
}
