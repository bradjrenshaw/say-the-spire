import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.Slider;
import sayTheSpire.Output;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.elements.SliderElement;

public class SliderPatch {

    @SpirePatch(clz = Slider.class, method = "update")
    public static class updatePatch {
        public static void Prefix(Slider __instance) {
            if (__instance.bgHb.justHovered) {
                SliderElement currentSlider = new SliderElement(__instance);
                UIRegistry.register(__instance, currentSlider);
                Output.setUI(currentSlider);
            }
        }
    }

    @SpirePatch(clz = Slider.class, method = "modifyVolume")
    public static class modifyVolumePatch {

        public static void Postfix(Slider __instance) {
            SliderElement currentSlider = (SliderElement) UIRegistry.getUI(__instance);
            if (__instance == currentSlider.getSlider()) {
                currentSlider.onVolumeChange();
            }
        }
    }
}
