import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.ToggleButton;
import sayTheSpire.Output;
import sayTheSpire.ui.SettingsToggleButtonElement;
import sayTheSpire.ui.UIRegistry;

public class ToggleButtonPatch {

    @SpirePatch(clz = ToggleButton.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(ToggleButton __instance) {
            if (__instance.hb.justHovered) {
                SettingsToggleButtonElement toggleElement = new SettingsToggleButtonElement(__instance);
                UIRegistry.register(__instance, toggleElement);
                Output.setUI(toggleElement);
            }
        }
    }

    @SpirePatch(clz = ToggleButton.class, method = "toggle")
    public static class TogglePatch {

        public static void Postfix(ToggleButton __instance) {
            SettingsToggleButtonElement currentToggle = (SettingsToggleButtonElement) UIRegistry.getUI(__instance);
            if (currentToggle == null)
                return;
            if (__instance == currentToggle.getButton()) {
                try {
                    currentToggle.onToggle();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
