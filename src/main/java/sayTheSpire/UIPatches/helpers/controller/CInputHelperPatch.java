import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import sayTheSpire.Output;

public class CInputHelperPatch {

    @SpirePatch(clz = CInputHelper.class, method = "initializeIfAble")
    public static class InitializeIfAblePatch {

        public static void Postfix() {
            if (Output.config.getBoolean("input.virtual_input") && CInputHelper.controller == null) {
                CInputHelper.model = CInputHelper.ControllerModel.XBOX_ONE;
                ImageMaster.loadControllerImages(CInputHelper.ControllerModel.XBOX_ONE);
            }
        }
    }

    @SpirePatch(clz = CInputHelper.class, method = "listenerPress", paramtypez = { int.class })
    public static class ListenerPressPatch {

        public static SpireReturn<Boolean> Prefix(int keycode) {
            Output.silenceSpeech();
            if (Output.getAllowVirtualInput()) {
                Output.inputManager.handleControllerKeycodePress(keycode);
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CInputHelper.class, method = "listenerRelease", paramtypez = { int.class })
    public static class ListenerReleasePatch {

        public static SpireReturn<Boolean> Prefix(int keycode) {
            if (Output.getAllowVirtualInput()) {
                Output.inputManager.handleControllerKeycodeRelease(keycode);
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CInputHelper.class, method = "updateLast")
    public static class UpdateLastPatch {

        public static SpireReturn Prefix() {
            if (Output.getAllowVirtualInput()) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
