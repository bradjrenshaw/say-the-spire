import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.GameOverStat;
import java.util.HashSet;
import sayTheSpire.Output;

public class GameOverStatPatch {

    public static HashSet<GameOverStat> hiddenStats = new HashSet();

    @SpirePatch(clz = GameOverStat.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { String.class, String.class,
            String.class })
    public static class ConstructorPatch {

        public static void Postfix(GameOverStat __instance, String label, String description, String value) {
            hiddenStats.add(__instance);
        }
    }
    /*
     * @SpirePatch(clz=GameOverStat.class, method=SpirePatch.CONSTRUCTOR, paramtypez={}) public static class
     * EmptyConstructorPatch {
     * 
     * public static void Postfix(GameOverStat __instance) { hiddenStats.add(__instance); } }
     */

    @SpirePatch(clz = GameOverStat.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(GameOverStat __instance) {
            if (hiddenStats.contains(__instance) && !__instance.hidden) {
                hiddenStats.remove(__instance);
                Output.textLocalized("ui.elements.game stat.label", false, "name", __instance.label, "value",
                        __instance.value);
            } else if (__instance.hb.justHovered) {
                Output.textLocalized("ui.elements.game stat.label", true, "name", __instance.label, "value",
                        __instance.value);
                if (__instance.description != null)
                    Output.setupUIBufferMany(__instance.label, __instance.description, __instance.value);
                else
                    Output.setupUIBufferMany(__instance.label, __instance.value);
            }
        }
    }
}
