import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import sayTheSpire.Output;

@SpirePatch(clz = MainMenuPanelButton.class, method = "update")
public class MainMenuPanelButtonPatch {

  public static void Postfix(MainMenuPanelButton __instance) {
    if (__instance.hb.justHovered) {
      String header =
          (String) ReflectionHacks.getPrivate(__instance, MainMenuPanelButton.class, "header");
      String description =
          (String) ReflectionHacks.getPrivate(__instance, MainMenuPanelButton.class, "description");
      Output.text(header + " button", true);
      Output.setupUIBufferMany(header, description);
    }
  }
}
