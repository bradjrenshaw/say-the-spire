import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import sayTheSpire.utils.InputUtils;
import sayTheSpire.Output;


public class InputHelperPatch {

      @SpirePatch(clz=InputHelper.class, method="updateLast")
  public static class UpdateLastPatch {
    
    public static SpireReturn Prefix() {
      if (Output.getAllowVirtualInput()) {
        Output.inputManager.updateLast();
        return SpireReturn.Return(null);
      }
      Output.updateInfoControls();
      return SpireReturn.Continue();
    }
  }
}
