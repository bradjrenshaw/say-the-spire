import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.AbandonRunButton;
import sayTheSpire.Output;

@SpirePatch(clz = AbandonRunButton.class, method = "update")
public class AbandonRunButtonPatch {

    public static void Postfix(AbandonRunButton __instance) {
        Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(__instance, AbandonRunButton.class, "hb");
        if (hb.justHovered) {
            Output.text(AbandonRunButton.TEXT[0] + " button", true);
            Output.setupUIBufferMany(AbandonRunButton.TEXT[0]);
        }
    }
}
