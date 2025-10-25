import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import sayTheSpire.Output;
import sayTheSpire.events.ProceedButtonShowEvent;

public class ProceedButtonPatch {

    public static boolean prevIsHidden = false;

    @SpirePatch(clz = ProceedButton.class, method = "show")
    public static class ShowPatch {

        public static void Postfix(ProceedButton __instance) {
            boolean isHidden = (boolean) ReflectionHacks.getPrivate(__instance, ProceedButton.class, "isHidden");
            if (!isHidden && isHidden != prevIsHidden) {
                String label = (String) ReflectionHacks.getPrivate(__instance, ProceedButton.class, "label");

                if (Output.config.getBoolean("ui.read_proceed_text"))
                    Output.event(new ProceedButtonShowEvent(__instance, label));
                prevIsHidden = isHidden;
            }
        }
    }

    @SpirePatch(clz = ProceedButton.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(ProceedButton __instance) {
            prevIsHidden = (boolean) ReflectionHacks.getPrivate(__instance, ProceedButton.class, "isHidden");
        }
    }
}
