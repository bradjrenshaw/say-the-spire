import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.AbandonRunButton;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.Output;

@SpirePatch(clz = AbandonRunButton.class, method = "update")
public class AbandonRunButtonPatch {

    public static void Postfix(AbandonRunButton __instance) {
        Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(__instance, AbandonRunButton.class, "hb");
        if (hb.justHovered) {
            ButtonElement button = new ButtonElement(AbandonRunButton.TEXT[0]);
            Output.setUI(button);
        }
    }
}
