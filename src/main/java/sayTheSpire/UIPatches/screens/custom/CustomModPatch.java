import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CustomModElement;
import sayTheSpire.ui.UIRegistry;

public class CustomModPatch {

    @SpirePatch(clz = CustomMod.class, method = "update")
    public static class updatePatch {

        public static void Postfix(CustomMod __instance) {
            if (__instance.hb.justHovered) {
                CustomModElement currentMod = new CustomModElement(__instance);
                UIRegistry.register(__instance, currentMod);
                Output.setUI(currentMod);
            }
        }
    }

    @SpirePatch(clz = CustomMod.class, method = "playClickSound")
    public static class PlayClickSoundPatch {

        public static void Postfix(CustomMod __instance) {
            CustomModElement currentMod = (CustomModElement) UIRegistry.getUI(__instance);
            if (currentMod != null) {
                currentMod.onToggle();
            }
        }
    }
}
