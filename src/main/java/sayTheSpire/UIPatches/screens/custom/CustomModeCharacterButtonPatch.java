import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import sayTheSpire.Output;
import sayTheSpire.ui.ToggleButtonElement;

public class CustomModeCharacterButtonPatch {

  @SpirePatch(clz = CustomModeCharacterButton.class, method = "update")
  public static class UpdatePatch {

    public static void Postfix(CustomModeCharacterButton __instance) {
      if (__instance.hb.justHovered) {
        ToggleButtonElement currentButton =
            new ToggleButtonElement(__instance.c.title, __instance.selected);
        currentButton.setStatusTexts("selected", "unselected");
        Output.setUI(currentButton);
      }
    }
  }
}
