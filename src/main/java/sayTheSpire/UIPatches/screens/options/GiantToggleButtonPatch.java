import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.GiantToggleButton;
import sayTheSpire.Output;

@SpirePatch(clz = GiantToggleButton.class, method = "update")
public class GiantToggleButtonPatch {

    public static void Postfix(GiantToggleButton __instance) {
        Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(__instance, GiantToggleButton.class, "hb");
        if (hb.justHovered) {
            String label = (String) ReflectionHacks.getPrivate(__instance, GiantToggleButton.class, "label");
            Output.text(label + " button", true);
        }
    }
}
