import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import sayTheSpire.utils.InputUtils;
import sayTheSpire.Output;

public class CInputHelperPatch {
  
  @SpirePatch(clz = CInputHelper.class, method = "listenerPress", paramtypez={int.class})
  public static class ListenerPressPatch {
    
    public static SpireReturn<Boolean> Prefix(int keycode) {
      //Output.silenceSpeech();
      if (Output.config.getBoolean("input.virtual_input")) {
        Output.inputManager.handleControllerKeycodePress(keycode);
        return SpireReturn.Return(false);
      }
      return SpireReturn.Continue();
    }
  }
  
  @SpirePatch(clz = CInputHelper.class, method = "listenerRelease", paramtypez={int.class})
  public static class ListenerReleasePatch {
    
    public static SpireReturn<Boolean> Prefix(int keycode) {
      if (Output.config.getBoolean("input.virtual_input")) {
        Output.inputManager.handleControllerKeycodeRelease(keycode);
        return SpireReturn.Return(false);
      }
      return SpireReturn.Continue();
    }
  }  
  
  @SpirePatch(clz=CInputHelper.class, method="updateLast")
  public static class UpdateLastPatch {
    
    public static SpireReturn Prefix() {
      if (Output.config.getBoolean("input.virtual_input")) {
        Output.inputManager.updateLast();
        return SpireReturn.Return(null);
      }
      Output.updateInfoControls();
      return SpireReturn.Continue();
    }
  }
}
