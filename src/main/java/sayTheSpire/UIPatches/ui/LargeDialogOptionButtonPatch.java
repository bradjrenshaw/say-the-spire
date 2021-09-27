import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import sayTheSpire.Output;
import sayTheSpire.ui.LargeDialogOptionButtonElement;

@SpirePatch(clz = LargeDialogOptionButton.class, method = "update")
public class LargeDialogOptionButtonPatch {

    public static void Prefix(LargeDialogOptionButton __instance) {
        if (__instance.hb.justHovered) {
            Output.setUI(new LargeDialogOptionButtonElement(__instance));
        }
    }
}
