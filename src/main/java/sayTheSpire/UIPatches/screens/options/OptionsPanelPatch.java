import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import com.megacrit.cardcrawl.screens.options.Slider;
import sayTheSpire.Output;

public class OptionsPanelPatch {

  @SpirePatch(clz = OptionsPanel.class, method = "update")
  public static class UpdatePatch {
    public static void handleInfoControls(OptionsPanel panel) {
      Slider masterSlider =
          (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "masterSlider");
      Slider bgmSlider =
          (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "bgmSlider");
      Slider sfxSlider =
          (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "sfxSlider");
      if (masterSlider.bgHb.hovered || bgmSlider.bgHb.hovered || sfxSlider.bgHb.hovered) {
        Output.bufferContext = "";
      } else {
        Output.bufferContext = "options";
      }
    }

    public static void Postfix(OptionsPanel __instance) {
      handleInfoControls(__instance);
    }
  }
}
