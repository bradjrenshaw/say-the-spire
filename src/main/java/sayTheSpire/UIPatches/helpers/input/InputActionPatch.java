import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import sayTheSpire.Output;

public class InputActionPatch {


    @SpirePatch(clz=InputAction.class, method="isJustPressed")
    public static class isJustPressedPatch {

        public static SpireReturn<Boolean> Prefix(InputAction action) {





            if (Output.config.getBoolean("input.virtual_input")) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz=InputAction.class, method="isPressed")
    public static class isPressedPatch {

        public static SpireReturn<Boolean> Prefix(InputAction action) {
            if (Output.config.getBoolean("input.virtual_input")) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }
}
