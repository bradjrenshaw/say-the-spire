import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.StorePotion;
import java.util.HashSet;
import sayTheSpire.Output;
import sayTheSpire.utils.PotionUtils;

@SpirePatch(clz = StorePotion.class, method = "update")
public class StorePotionPatch {

    public static HashSet<StorePotion> storePotions = new HashSet();
    public static StorePotion hoveredPotion = null;

    public static void Postfix(StorePotion __instance) {
        if (__instance.potion.hb.hovered) {
            if (__instance != hoveredPotion) {
                storePotions.add(__instance);
                Output.text(PotionUtils.getPotionShort(__instance), true);
                if (hoveredPotion != null) {
                    storePotions.remove(hoveredPotion);
                }
                hoveredPotion = __instance;
                Output.setupBuffers(__instance.potion);
            }
        } else {
            storePotions.remove(__instance);
        }
        if (storePotions.isEmpty()) {
            hoveredPotion = null;
        }
    }
}
