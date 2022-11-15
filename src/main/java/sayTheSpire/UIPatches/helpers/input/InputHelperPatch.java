import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import sayTheSpire.InfoControls;
import sayTheSpire.Output;

public class InputHelperPatch {

    @SpirePatch(clz = InputHelper.class, method = "updateLast")
    public static class UpdateLastPatch {

        public static SpireReturn Prefix() {
            if (Output.getAllowVirtualInput()) {
                Output.uiManager.updateLast();
                return SpireReturn.Return(null);
            }
            InfoControls.update();
            return SpireReturn.Continue();
        }
    }
}
