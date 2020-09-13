import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import sayTheSpire.utils.InputUtils;
import sayTheSpire.Output;

@SpirePatch(clz = CInputHelper.class, method = "listenerPress")
public class CInputHelperPatch {

  public static void Postfix() {
    for (CInputAction action:InputUtils.getCInputActions()) {
      if (action.isJustPressed()) {
        Output.silenceSpeech();
        break;
      }
    }
    Output.infoControls();
  }
}
