import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.SortHeaderButtonElement;
import sayTheSpire.ui.UIRegistry;

public class SortHeaderButtonPatch {

    @SpirePatch(clz = SortHeaderButton.class, method = "update")
    public static class update {

        public static void Postfix(SortHeaderButton __instance) {
            if (__instance.hb.justHovered) {
                SortHeaderButtonElement element = new SortHeaderButtonElement(__instance);
                UIRegistry.register(__instance, element);
                Output.setUI(element);
            }
        }
    }
}
