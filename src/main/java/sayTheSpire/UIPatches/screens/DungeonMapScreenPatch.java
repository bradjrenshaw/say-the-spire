import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import sayTheSpire.Output;
import sayTheSpire.utils.MapUtils;

@SpirePatch(clz = DungeonMapScreen.class, method = "open", paramtypez = { boolean.class })
public class DungeonMapScreenPatch {

    public static void Postfix(DungeonMapScreen __instance, boolean doScrollingAnimation) {
        Output.text("map", true);
        if (doScrollingAnimation) {
            Output.textLocalized("ui.screens.DungeonMapScreen.boss scroll", false, "boss",
                    MapUtils.getLocalizedBossName());
        }
        Output.setupBuffers(MapUtils.getCurrentNode(), false, true);
    }
}
