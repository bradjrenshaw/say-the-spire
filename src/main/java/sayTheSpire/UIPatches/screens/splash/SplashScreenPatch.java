import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;
import sayTheSpire.Output;

public class SplashScreenPatch {

    public static Boolean announcedVersion = false;

    @SpirePatch(clz = SplashScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(SplashScreen __instance) {
            if (__instance.isDone && !announcedVersion) {
                Output.announceVersion();
                announcedVersion = true;
            }
        }
    }
}
