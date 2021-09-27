import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.StoreRelic;
import java.util.HashSet;
import sayTheSpire.Output;
import sayTheSpire.utils.RelicUtils;

@SpirePatch(clz = StoreRelic.class, method = "update")
public class StoreRelicPatch {

    public static HashSet<StoreRelic> storeRelics = new HashSet();
    public static StoreRelic hoveredStoreRelic = null;

    public static void Postfix(StoreRelic __instance) {
        if (__instance.relic.hb.hovered) {
            if (__instance != hoveredStoreRelic) {
                storeRelics.add(__instance);
                Output.text(RelicUtils.getRelicShort(__instance), true);
                if (hoveredStoreRelic != null) {
                    storeRelics.remove(hoveredStoreRelic);
                }
                hoveredStoreRelic = __instance;
                Output.setupBuffers(__instance.relic);
            }
        } else {
            storeRelics.remove(__instance);
        }
        if (storeRelics.isEmpty()) {
            hoveredStoreRelic = null;
        }
    }
}
