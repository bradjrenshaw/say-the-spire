import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.GiantToggleButton;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.ButtonElement;

@SpirePatch(clz = GiantToggleButton.class, method = "update")
public class GiantToggleButtonPatch {

    public static void Postfix(GiantToggleButton __instance) {
        Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(__instance, GiantToggleButton.class, "hb");
        if (hb.justHovered) {
            String label = (String) ReflectionHacks.getPrivate(__instance, GiantToggleButton.class, "label");
            ButtonElement button = new ButtonElement(label);
            Output.setUI(button);
        }
    }
}
