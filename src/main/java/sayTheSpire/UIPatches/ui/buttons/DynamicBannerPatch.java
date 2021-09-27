import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import sayTheSpire.Output;

public class DynamicBannerPatch {

    @SpirePatch(clz = DynamicBanner.class, method = "appear", paramtypez = { float.class, String.class })
    @SpirePatch(clz = DynamicBanner.class, method = "appearInstantly", paramtypez = { float.class, String.class })
    public static class AppearPatch {

        public static void Postfix(DynamicBanner __instance, float y, String label) {
            if (Output.config.getBoolean("ui.read_banner_text"))
                Output.text(label, false);
        }
    }
}
