import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.DeathScreen;
import sayTheSpire.Output;

public class DeathScreenPatch {

    public static Boolean hasReadDeathText = false;

    /*
     * @SpirePatch(clz=DeathScreen.class, method="reopen", paramtypez={boolean.class}) public static class ReopenPatch {
     * 
     * public static void Postfix(DeathScreen __instance, boolean victory) { hasReadDeathText = false; } }
     */
    @SpirePatch(clz = DeathScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DeathScreen __instance) {
            Float timer = (float) ReflectionHacks.getPrivate(__instance, DeathScreen.class, "deathTextTimer");
            if (timer > 0.0) {
                hasReadDeathText = false;
            } else if (timer <= 0.0 && !hasReadDeathText) {
                String deathText = (String) ReflectionHacks.getPrivate(__instance, DeathScreen.class, "deathText");
                Output.text(deathText, false);
                hasReadDeathText = true;
            }
        }
    }
}
